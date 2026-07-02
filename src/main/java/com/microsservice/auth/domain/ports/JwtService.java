package com.microsservice.auth.domain.ports;

import java.util.Date;
import java.util.UUID;

public interface JwtService {
    String generateToken(UUID userId, String email, String role);
    boolean isTokenValid(String token);
    String extractRole(String token);

    String extractEmail(String token);

    String extractJti(String token);
    Date extractExpiration(String token);
    String extractUserId(String token);
}
