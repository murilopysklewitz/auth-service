package com.microsservice.auth.domain.ports;

import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.User;

import java.util.UUID;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    RefreshToken findById(UUID id);

    void deleteById(UUID refreshTokenId);


    void revokeTokensForUser(UUID userId);
    void deleteExpiredTokens();
}
