package com.microsservice.auth.domain;

import java.util.Date;

public interface JwtService {
    String generateToken(String userId, String role);
    boolean isTokenValid(String token);
    String extractRole(String token);
    Date extractExpiration(String token);
    String extractUserId(String token);
}
