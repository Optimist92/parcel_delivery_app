package com.test.msorders.services.mappers;

import com.test.msorders.domain.Order;
import dto.OrderDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IOrderMapper {
    //@Mapping( target = "category.title", source = "category")
    Order dtoToEntity(OrderDTO dto);

    //@Mapping(target = "courierName", source = "courier.username")
    //@Mapping(target = "customerName", source = "customer.username")
    OrderDTO entityToDto(Order entity);
}