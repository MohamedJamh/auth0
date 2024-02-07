package com.auth0.domain.mapper;

import com.auth0.domain.dto.request.role.RoleRequestDto;
import com.auth0.domain.dto.response.role.RoleResponseDto;
import com.auth0.domain.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponseDto toDto(Role user);
    Role toUser(RoleRequestDto userDto);
}
