package com.auth0.domain.dto.request.role;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleRequestDto {
    private String name;
}
