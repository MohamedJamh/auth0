package com.auth0.domain.dto.response.user;

import com.auth0.domain.dto.response.role.RoleResponseDto;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<RoleResponseDto> roles;
}
