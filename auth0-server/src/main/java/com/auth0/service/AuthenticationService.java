package com.auth0.service;

import com.auth0.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.auth0.domain.entity.User;
import com.auth0.exception.customexceptions.BadRequestException;
import com.auth0.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtAuthenticationResponseDto signup(User request) throws ValidationException;
    JwtAuthenticationResponseDto signin(User request) throws BadRequestException;
}
