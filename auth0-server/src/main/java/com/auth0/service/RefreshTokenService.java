package com.auth0.service;

import com.auth0.domain.entity.RefreshToken;
import com.auth0.exception.customexceptions.InValidRefreshTokenException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface RefreshTokenService {
    RefreshToken getOrCreateRefreshToken(String email) throws UsernameNotFoundException;
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token) throws InValidRefreshTokenException;
    void throwInValidRefreshTokenException(String message) throws InValidRefreshTokenException;
    void delete(RefreshToken token);
}
