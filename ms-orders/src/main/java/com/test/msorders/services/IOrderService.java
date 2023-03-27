package com.test.msorders.services;

import com.test.msorders.domain.Order;
import dto.OrderStatusDTO;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    Order findByPublicId(UUID id);

    List<Order> findAll();

    List<Order> findOrdersByCustomer(String token);

    Order create(String token, Order order);

    Order updateOrderStatusByAdmin(OrderStatusDTO orderStatusDTO);

    Order closeOrderByCourier(String token, OrderStatusDTO orderStatusDTO);

    Order assignOrderToCourier(OrderStatusDTO orderStatusDTO);

    Order update(Order order);

    Order delete(UUID id);

    int quoteOrder(Order order);
}
