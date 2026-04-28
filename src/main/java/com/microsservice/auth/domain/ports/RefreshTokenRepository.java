package com.microsservice.auth.domain.ports;

import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.User;

import java.util.UUID;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    RefreshToken findByTokenId(UUID tokenId);
    void delete(UUID refreshTokenId);
    void revokeTokensForUser(UUID userId);
    void deleteExpiredTokens();
}
