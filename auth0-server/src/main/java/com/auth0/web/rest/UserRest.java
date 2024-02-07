package com.auth0.web.rest;

import com.auth0.domain.dto.response.user.UserResponseDto;
import com.auth0.domain.entity.User;
import com.auth0.domain.mapper.UserMapper;
import com.auth0.repository.UserRepository;
import com.auth0.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
@RequiredArgsConstructor
public class UserRest {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public ResponseEntity<Response<List<UserResponseDto>>> getAllUsers() {
        Response<List<UserResponseDto>> response = new Response<>();
        List<User> users = userRepository.findAll();
        response.setResult(
                users.stream()
                        .map(userMapper::toDto)
                        .toList()
        );
        response.setMessage("Users retrieved successfully");
        return ResponseEntity.ok().body(response);
    }
}
