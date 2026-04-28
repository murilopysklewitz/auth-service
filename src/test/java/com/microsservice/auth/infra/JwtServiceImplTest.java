package com.microsservice.auth.infra;

import com.microsservice.auth.infra.security.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.UUID;

public class JwtServiceImplTest {
    private UUID userId;
    private String role;
    @Mock
    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
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
