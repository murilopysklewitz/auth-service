package com.microsservice.auth.domain.ports;

import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.User;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    RefreshToken findByToken(String token);
    void delete(RefreshToken refreshToken);
    void revokeTokensForUser(User user);
    void deleteExpiredTokens();
}
