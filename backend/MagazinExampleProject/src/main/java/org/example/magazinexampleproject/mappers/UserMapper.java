package org.example.magazinexampleproject.mappers;

import org.example.magazinexampleproject.dto.user.UserDto;
import org.example.magazinexampleproject.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Явно указываем соответствие полей

    UserDto toDto(User user); // Маппинг User в UserDto

    // Явно указываем соответствие полей

    User toEntity(UserDto userDto); // Маппинг UserDto в User
}
