package com.test.msorders.services.mappers;

import com.test.msorders.domain.Order;
import payload.OrderPayload;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IOrderPayloadMapper {
    //@Mapping( target = "category.title", source = "category")
    Order dtoToEntity(OrderPayload dto);
    //@Mapping(target = "category", source = "category.title")
}