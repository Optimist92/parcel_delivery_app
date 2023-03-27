package com.test.msorders.repository;

import com.test.msorders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findById(UUID id);
    Optional<Order> findByPublicId(UUID id);
    List<Order> findAll();
    List<Order> findAllByCustomerPublicId(Long publicId);

    Optional<Order> findOrderByPublicId(UUID publicId);

    List<Order> findAllByCourierPublicId(Long publicId);

    /*@Modifying
    //@Query("update Order order set order.status = enums.EOrderStatus.STATUS_QUOTED, order.cost = :newCost where order.id = :id")
    @Query("update Order ord set ord.cost = :newCost where ord.id = :id")
    int quoteOrder(@Param("id") UUID id, @Param("newCost") BigDecimal newCost);*/

    @Modifying
    @Transactional
    @Query("update Order order set order.status = enums.EOrderStatus.STATUS_QUOTED, order.cost = :cost where order.publicId = :id")
        //@Query("update Order ord set ord.content = :newCost where ord.id = :id")
    int updateQuotedOrder(@Param("id") UUID id, @Param("cost") BigDecimal cost);



    @Modifying
    @Transactional
    @Query("update Order order set order.status = enums.EOrderStatus.STATUS_CONFIRMED where order.id = :id")
    int confirmOrder(@Param("id") UUID id);

}