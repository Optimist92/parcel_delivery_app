package com.test.msorders.controllers.v1;

import com.test.msorders.domain.Order;
import com.test.msorders.services.impl.OrderService;
import com.test.msorders.services.mappers.IOrderMapper;
import payload.OrderPayload;
import com.test.msorders.services.mappers.IOrderPayloadMapper;
import dto.OrderDTO;
import dto.OrderStatusDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final IOrderPayloadMapper orderPayloadMapper;
    private final IOrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDTO> createDraftOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerToken, @RequestBody OrderPayload dto) {
        Order draftOrder = orderPayloadMapper.dtoToEntity(dto);
        var order = orderService.create(headerToken, draftOrder);
        return ResponseEntity.created(URI.create("http://localhost:8082/v1/orders/" + order.getId())).body(orderMapper.entityToDto(order));
    }

    @GetMapping("/{public_id}")
    public ResponseEntity<OrderDTO> findOrderByPublicId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String public_id) {
        Order order = orderService.findByPublicId(UUID.fromString(public_id));
        orderService.validMatchOrderUsers(token, order);
        return ResponseEntity.ok().body(orderMapper.entityToDto(order));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<OrderDTO>> findOrdersByCustomer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Order> orders = orderService.findOrdersByCustomer(token);
        return ResponseEntity.ok().body(orders.stream().map(orderMapper::entityToDto).collect(Collectors.toList()));
    }
    @GetMapping("/courier")
    public ResponseEntity<List<OrderDTO>> findOrdersByCourier(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Order> orders = orderService.findOrdersByCourier(token);
        return ResponseEntity.ok().body(orders.stream().map(orderMapper::entityToDto).collect(Collectors.toList()));
    }

    @PostMapping("/courier/close_order")
    public ResponseEntity<OrderDTO> orderClosingByCourier(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok().body(orderMapper.entityToDto(orderService.closeOrderByCourier(token, dto)));
    }

    @PostMapping("/customer/confirm_order")
    public ResponseEntity<OrderDTO> orderAcceptingByCustomer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok().body(orderMapper.entityToDto(orderService.acceptOrderByCustomer(token, dto)));
    }

    @PostMapping("/customer/close_order")
    public ResponseEntity<OrderDTO> orderClosingByCustomer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok().body(orderMapper.entityToDto(orderService.closeOrderByCustomer(token, dto)));
    }

    /*@GetMapping("/test")
    public Integer test() {
        return orderRepository.quoteOrder(UUID.randomUUID(), "Привет");
    }*/

}