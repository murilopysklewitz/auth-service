package com.microsservice.auth.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class JwtServiceImplTest {
    private String userId;
    private String role;
    @Mock
    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        userId = "12345";
        role = "USER";
        jwtService = new JwtServiceImpl("test-secret-test-secret-test-secret", 3600000);
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtService.generateToken(userId, role);
        assert jwtService.isTokenValid(token);
        assert jwtService.extractUserId(token).equals(userId);
        assert jwtService.extractRole(token).equals(role);
    }
}
