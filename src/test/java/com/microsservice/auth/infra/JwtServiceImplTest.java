package com.microsservice.auth.infra;

import com.microsservice.auth.infra.security.JwtProperties;
import com.microsservice.auth.app.service.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    private UUID userId;
    private String role;
    private String email;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        email = "abc@gmail.com";
        role = "USER";

        JwtProperties props = new JwtProperties();
        props.setSecret("12345678901234567890123456789012");
        props.setExpiration(1500000L);

        jwtService = new JwtServiceImpl(props);
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtService.generateToken(userId, email,  role);

        assertTrue(jwtService.isTokenValid(token));
        assertEquals(role, jwtService.extractRole(token));
        assertEquals(userId.toString(), jwtService.extractUserId(token));
    }
}