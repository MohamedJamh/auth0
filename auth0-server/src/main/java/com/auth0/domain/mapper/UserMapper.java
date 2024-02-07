package com.auth0.domain.mapper;

import com.auth0.domain.dto.request.user.UserRequestDto;
import com.auth0.domain.dto.response.user.UserResponseDto;
import com.auth0.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponseDto toDto(User user);
    User toUser(UserRequestDto userDto);
}
