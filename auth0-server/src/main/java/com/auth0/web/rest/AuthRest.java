package com.auth0.web.rest;

import com.auth0.domain.dto.request.auth.SigninRequestDto;
import com.auth0.domain.dto.request.jwt.RefreshTokenRequestDto;
import com.auth0.domain.dto.request.user.UserRequestDto;
import com.auth0.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.auth0.domain.dto.response.jwt.RefreshTokenResponseDTO;
import com.auth0.domain.entity.RefreshToken;
import com.auth0.domain.entity.User;
import com.auth0.domain.mapper.UserMapper;
import com.auth0.exception.customexceptions.BadRequestException;
import com.auth0.exception.customexceptions.InValidRefreshTokenException;
import com.auth0.exception.customexceptions.ValidationException;
import com.auth0.service.AuthenticationService;
import com.auth0.service.JwtService;
import com.auth0.service.RefreshTokenService;
import com.auth0.service.UserService;
import com.auth0.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRest {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private JwtService jwtService;
    private UserService userService;
    private UserMapper userMapper;

    public AuthRest(
            AuthenticationService authenticationService,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            UserService userService,
            UserMapper userMapper
    ) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<JwtAuthenticationResponseDto>> signup(@RequestBody @Valid UserRequestDto userRequestDto) throws ValidationException {
        Response<JwtAuthenticationResponseDto> response = new Response<>();
        User user = userMapper.toUser(userRequestDto);
        JwtAuthenticationResponseDto jwtAuthenticationResponseDto = authenticationService.signup(user);
        jwtAuthenticationResponseDto.setRefreshToken(
                refreshTokenService.getOrCreateRefreshToken(user.getEmail())
                .getToken()
        );
        response.setResult(jwtAuthenticationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<Response<JwtAuthenticationResponseDto>> signin(@RequestBody @Valid SigninRequestDto signinRequestDto) throws BadRequestException {
        Response<JwtAuthenticationResponseDto> response = new Response<>();
        User user = User.builder()
                .email(signinRequestDto.getEmail())
                .password(signinRequestDto.getPassword())
                .build();
        JwtAuthenticationResponseDto jwtAuthenticationResponseDto =  authenticationService.signin(user);
        jwtAuthenticationResponseDto.setRefreshToken(
                refreshTokenService.getOrCreateRefreshToken(user.getEmail())
                        .getToken()
        );
        response.setResult(jwtAuthenticationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refreshToken")
    public RefreshTokenResponseDTO refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) throws InValidRefreshTokenException {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken());
        RefreshToken refreshToken = null;
        if (optionalRefreshToken.isEmpty())
            refreshTokenService.throwInValidRefreshTokenException("invalid refresh token.");
        else {
            refreshToken = optionalRefreshToken.get();
            if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
                refreshTokenService.delete(refreshToken);
                refreshTokenService.throwInValidRefreshTokenException("Refresh token was expired. Please make a new signin.");
            }
        }
        UserDetails userDetails = userService.getUserIfExitOrThrowException(refreshToken.getUser().getEmail());
        String accessToken = jwtService.generateToken(userDetails);
        return RefreshTokenResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }
}
