package com.test.msorders.queue.listener;

import com.test.msorders.services.impl.OrderService;
import com.test.msorders.services.mappers.IOrderMapper;
import dto.OrderDTO;
import enums.EOrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuotedOrdersListener {
    private final OrderService orderService;
    private final IOrderMapper orderMapper;
    @RabbitListener(queues = "quote-orders-queue")
    public void worker1(OrderDTO orderDTO) throws InterruptedException {
        log.info("Получен расчитанный заказ: " + orderDTO.getPublicId());
        orderDTO.setStatus(EOrderStatus.STATUS_QUOTED);
        orderService.update(orderMapper.dtoToEntity(orderDTO));
        log.info(String.format("Расчитаный заказ №%s обновлён", orderDTO.getPublicId()));
        System.out.println("Расчитаный заказ №%s обновлён");
    }
}
