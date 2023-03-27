package com.test.mscouriers.mapper;

import com.test.mscouriers.domain.Courier;
import dto.CourierDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourierMapper {
    Courier dtoToEntity(CourierDTO dto);

    CourierDTO entityToDto(Courier entity);
}
