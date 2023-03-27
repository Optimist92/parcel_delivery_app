package com.test.msidentities.mapper;

import com.test.msidentities.model.User;
import dto.UserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    User dtoToEntity(UserDTO dto);

    UserDTO entityToDto(User entity);
}
