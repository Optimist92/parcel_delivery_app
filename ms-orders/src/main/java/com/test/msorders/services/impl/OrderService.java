package com.test.msorders.services.impl;

import com.test.msorders.domain.Order;
import com.test.msorders.exception.ChangeStatusException;
import com.test.msorders.exception.UserAccessException;
import com.test.msorders.repository.IOrderRepository;
import com.test.msorders.services.IOrderService;
import com.test.msorders.services.mappers.IOrderMapper;
import constants.RabbitConstants;
import dto.OrderDTO;
import dto.CourierDTO;
import util.JwtUtil;
import dto.OrderStatusDTO;
import enums.EOrderStatus;
import enums.EUserRole;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final static String IDENTITIES_URL = "http://localhost:8081/ms-identities/auth";

    private final static String COURIERS_URL = "http://localhost:8083/";
    private final IOrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    private final IOrderMapper orderMapper;

    private final JwtUtil jwtUtil;

    private final RestTemplate restTemplate;

    @Override
    public Order findByPublicId(UUID id) {
        return orderRepository.findByPublicId(id).orElseThrow(() -> new EntityNotFoundException("Order not found: id = " + id));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findOrdersByCustomer(String token) {
        return orderRepository.findAllByCustomerPublicId(Long.parseLong(jwtUtil.getPublicIdFromToken(token)));
    }

    public List<Order> findOrdersByCourier(String token) {
        return orderRepository.findAllByCourierPublicId(Long.parseLong(jwtUtil.getPublicIdFromToken(token)));
    }

    @Override
    @Transactional
    public Order create(String token, Order order) {
        Order savedOrder;
        if(!jwtUtil.getRoleFromToken(token).equals(EUserRole.ROLE_CUSTOMER.name())) {
            throw new UserAccessException("Пользователь с данной ролью не может сделать заказ");
        }
        String publicId = jwtUtil.getPublicIdFromToken(token);

        order.setId(UUID.randomUUID());
        order.setPublicId(UUID.randomUUID());
        order.setTitle(new Date().toString());
        order.setStatus(EOrderStatus.STATUS_DRAFT);
        order.setCreationDate(new Date());
        order.setCustomerPublicId(Long.parseLong(publicId));
        //TODO create save method
        try {
            savedOrder = orderRepository.save(order);
            //TODO save history
            rabbitTemplate.convertAndSend(RabbitConstants.DraftOrdersRoutingKey, orderMapper.entityToDto(savedOrder));

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Нарушение уникальности по полю" + ": " + e.getCause().getCause().getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return savedOrder;
    }

    @Override
    public Order updateOrderStatusByAdmin(OrderStatusDTO orderStatusDTO) {
        List<EOrderStatus> possiblyCloseStatusList = List.of(EOrderStatus.STATUS_DRAFT,
                EOrderStatus.STATUS_QUOTED, EOrderStatus.STATUS_FINISHED);
        EOrderStatus newStatus = orderStatusDTO.getStatus();
        Order order = orderRepository.findOrderByPublicId(UUID.fromString(orderStatusDTO.getPublicId())).orElseThrow(() -> new EntityNotFoundException("Order not found: publicId = " + orderStatusDTO.getPublicId()));
        if(newStatus.equals(EOrderStatus.STATUS_CLOSED) && possiblyCloseStatusList.contains(order.getStatus())) {
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        //TODO add logic to change other statuses
        throw new ChangeStatusException(String.format("Данный статус недоступен для заказа №%s", orderStatusDTO.getPublicId()));
    }

    public Order closeOrderByCourier(String token, OrderStatusDTO orderStatusDTO) {
        List<EOrderStatus> possiblyCloseStatusList = List.of(EOrderStatus.STATUS_FINISHED); //TODO need to add statuses?
        Order order = orderRepository.findOrderByPublicId(UUID.fromString(orderStatusDTO.getPublicId())).orElseThrow(() -> new EntityNotFoundException("Order not found: publicId = " + orderStatusDTO.getPublicId()));
        if(orderStatusDTO.getStatus().equals(EOrderStatus.STATUS_CLOSED) && possiblyCloseStatusList.contains(order.getStatus()) && validMatchOrderUsers(token, order)) {
            order.setStatus(orderStatusDTO.getStatus());
            return orderRepository.save(order);
        }
        throw new ChangeStatusException(String.format("Данный статус недоступен для заказа №%s", orderStatusDTO.getPublicId()));
    }

    public Order closeOrderByCustomer(String token, OrderStatusDTO orderStatusDTO) {
        List<EOrderStatus> possiblyCloseStatusList = List.of(EOrderStatus.STATUS_DRAFT, EOrderStatus.STATUS_QUOTED);
        Order order = orderRepository.findOrderByPublicId(UUID.fromString(orderStatusDTO.getPublicId())).orElseThrow(() -> new EntityNotFoundException("Order not found: publicId = " + orderStatusDTO.getPublicId()));
        if(orderStatusDTO.getStatus().equals(EOrderStatus.STATUS_CLOSED) && possiblyCloseStatusList.contains(order.getStatus()) && validMatchOrderUsers(token, order)) {
            order.setStatus(EOrderStatus.STATUS_CLOSED);
            return orderRepository.save(order);
        }
        throw new ChangeStatusException(String.format("Данный статус недоступен для заказа №%s", orderStatusDTO.getPublicId()));
    }

    @Override
    public Order assignOrderToCourier(OrderStatusDTO orderStatusDTO) {

        CourierDTO courier = restTemplate.getForObject(COURIERS_URL + "v1/couriers/" + orderStatusDTO.getCourierId(), CourierDTO.class);
        if(courier == null) {
            throw new EntityNotFoundException("Courier not found: publicId = " + orderStatusDTO.getCourierId());
        }
        if(!courier.getIsAvailable()) {
            throw new ChangeStatusException(String.format("Курьер №%d не доступен", courier.getUserPublicId()));
        }
        Order order = orderRepository.findOrderByPublicId(UUID.fromString(orderStatusDTO.getPublicId())).orElseThrow(() -> new EntityNotFoundException("Order not found: publicId = " + orderStatusDTO.getPublicId()));
        order.setCourierPublicId(orderStatusDTO.getCourierId());
        order.setStatus(EOrderStatus.STATUS_ASSIGNED);
        orderRepository.save(order);

        return orderRepository.save(order);
    }

    public Order acceptOrderByCustomer(String token, OrderStatusDTO orderStatusDTO) {
        Order order = orderRepository.findOrderByPublicId(UUID.fromString(orderStatusDTO.getPublicId())).orElseThrow(() -> new EntityNotFoundException("Order not found: publicId = " + orderStatusDTO.getPublicId()));
        if(orderStatusDTO.getStatus().equals(EOrderStatus.STATUS_CONFIRMED) && order.getStatus().name().equals(EOrderStatus.STATUS_QUOTED.name()) && validMatchOrderUsers(token, order)) {
            order.setStatus(orderStatusDTO.getStatus());
            //TODO передать в очередь для заморозки денег
            return orderRepository.save(order);
        }
        throw new ChangeStatusException(String.format("Данный статус недоступен для заказа №%s", orderStatusDTO.getPublicId()));
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order delete(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public OrderDTO processQuotedOrder(OrderDTO orderDTO) {
        orderRepository.updateQuotedOrder(orderDTO.getPublicId(), orderDTO.getCost());
        return orderDTO;
    }


    public boolean validMatchOrderUsers(String token, Order order) {
        String publicId = jwtUtil.getPublicIdFromToken(token);
        String role = jwtUtil.getRoleFromToken(token);
        UUID orderPublicId = order.getPublicId();
        order = orderRepository.findOrderByPublicId(orderPublicId).orElseThrow(() -> new EntityNotFoundException("Order not found: publicId = " + orderPublicId.toString()));
        if(role.equals(EUserRole.ROLE_CUSTOMER.name()) && !publicId.equals(order.getCustomerPublicId().toString())) {
            throw new UserAccessException(String.format("Заказ номер:%s отсутствует или создан другим пользователем", order.getPublicId().toString()));
        }
        if(role.equals(EUserRole.ROLE_COURIER.name()) && !publicId.equals(order.getCourierPublicId().toString())) {
            throw new UserAccessException(String.format("Заказ номер:%s отсутствует или не назначен на вас", order.getPublicId().toString()));
        }
        return true;
    }



    /*
    @Transactional
    public String startProcessQuotedOrders(Long minDelay, Long maxDelay) {

        var runnable = new Runnable() {
            @Override
            @Transactional
            public void run() {

                var counter = 0;
                QUEUE_QUOTE_PROCESSING = true;
                ObjectMapper mapper = new ObjectMapper();

                try(Connection connection = connectionFactory.createConnection();
                    Channel channel = connection.createChannel(false);) {

                    channel.queueDeclare(QUEUE_QUOTE_NAME, true, false, false, null);

                    while (!Thread.interrupted() | OrderService.QUEUE_QUOTE_PROCESSING) {

                        GetResponse resp = channel.basicGet(QUEUE_QUOTE_NAME, false);
                        if( resp != null ){
                            String message = new String(resp.getBody(), StandardCharsets.UTF_8);
                            //System.out.println(" [x] Received '" + message + "'");
                            //channel.basicNack(resp.getEnvelope().getDeliveryTag(), false, true);


                            OrderQuoteDTO dto = mapper.readValue(message, OrderQuoteDTO.class);
                            orderRepository.updateQuotedOrder(dto.getId(), dto.getCost());

                            channel.basicAck(resp.getEnvelope().getDeliveryTag(), false);
                            System.out.println("processing message " + message);
                        }

                        try {
                            sleep(DelayService.getDelay(minDelay, maxDelay));
                        } catch (InterruptedException e) {
                            System.out.println("interrupted exception");
                            Thread.currentThread().interrupt();
                            QUEUE_QUOTE_PROCESSING = false;
                            //do nothing, stop cycle
                        } catch (Exception e) {
                            System.out.println("another exception");
                            throw new RuntimeException(e);
                        }
                    }

                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                } finally {
                    QUEUE_QUOTE_PROCESSING = false;
                }
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        return thread.getName();
    }*/

    /*
    public String stopProcessQuotedOrders() {
        QUEUE_QUOTE_PROCESSING = false;
        return QUEUE_QUOTE_PROCESSING.toString();
    }*/

    /*
    public String getProcessQuotedOrdersStatus() {
        return QUEUE_QUOTE_PROCESSING.toString();
    }*/

    /*
    //@Scheduled(fixedRateString = "1000")
    public void acceptQuotedOrders() {

    }*/


    /*
    //@Scheduled(fixedRateString = "5000")
    public String processQuotedOrder() {

        String result = null;

        try(Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false)){

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // pull one message and ack manually and exit
            GetResponse resp0 = channel.basicGet(QUEUE_NAME, false);
            GetResponse resp = channel.basicGet(QUEUE_NAME, false);
            if( resp != null ){
                String message = new String(resp.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                result = message;
                channel.basicNack(resp.getEnvelope().getDeliveryTag(), false, true);
                //channel.basicAck(resp.getEnvelope().getDeliveryTag(), false);
            }
            System.out.println();
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }*/
}
