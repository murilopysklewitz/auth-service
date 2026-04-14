package com.microsservice.auth.domain;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    RefreshToken findByToken(String token);
    void delete(RefreshToken refreshToken);
    void revokeTokensForUser(User user);
    void deleteExpiredTokens();
}
