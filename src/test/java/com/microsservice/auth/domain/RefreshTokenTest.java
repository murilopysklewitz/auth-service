package com.microsservice.auth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RefreshTokenTest {
    private UUID tokenId;
    private UUID userId;
    private String email;
    private String role;
    private Instant expiresAt;
    private Instant createdAt;
    private boolean revoked;
    private Instant revokedAt;
    private Duration ttl;

    @BeforeEach
    void setUp() {
        tokenId = UUID.randomUUID();
        userId = UUID.randomUUID();
        email = "test@gmail.com";
        role = "USER";
        ttl = Duration.ofDays(7);
        expiresAt = Instant.now().plus(ttl);
        createdAt = Instant.now();
        revoked = false;
        revokedAt = null;
    }

    @Test
    void shouldCreateAValidRefreshToken() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, ttl);

        assertNotNull(refreshToken);
        assertNotNull(refreshToken.getId());
        assertEquals(userId, refreshToken.getUserId());
        assertEquals(role, refreshToken.getRole());
        assertTrue(refreshToken.getExpiresAt().isAfter(Instant.now()));
        assertTrue(refreshToken.getCreatedAt().isBefore(Instant.now().plusMillis(100)));
        assertFalse(refreshToken.isRevoked());
        assertNull(refreshToken.getRevokedAt());
        assertTrue(refreshToken.isValid());
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> RefreshToken.create(null, email,role, ttl));
    }


    @Test
    void shouldThrowExceptionWhenCreatingWithNullRole() {
        assertThrows(IllegalArgumentException.class, () -> RefreshToken.create(userId, email, null, ttl));
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithBlankRole() {
        assertThrows(IllegalArgumentException.class, () -> RefreshToken.create(userId, email, "", ttl));
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithNullTtl() {
        assertThrows(IllegalArgumentException.class, () -> RefreshToken.create(userId, email, role, null));
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithNegativeTtl() {
        assertThrows(IllegalArgumentException.class, () -> RefreshToken.create(userId, email, role, Duration.ofDays(-1)));
    }

    @Test
    void shouldThrowExceptionWhenCreatingWithZeroTtl() {
        assertThrows(IllegalArgumentException.class, () -> RefreshToken.create(userId, email, role, Duration.ZERO));
    }

    @Test
    void shouldRestoreRefreshToken() {
        RefreshToken refreshToken = RefreshToken.restore(tokenId, userId, email, role, expiresAt, createdAt, revoked, revokedAt);

        assertEquals(tokenId, refreshToken.getId());
        assertEquals(userId, refreshToken.getUserId());
        assertEquals(role, refreshToken.getRole());
        assertEquals(expiresAt, refreshToken.getExpiresAt());
        assertEquals(createdAt, refreshToken.getCreatedAt());
        assertEquals(revoked, refreshToken.isRevoked());
        assertEquals(revokedAt, refreshToken.getRevokedAt());
    }

    @Test
    void shouldBeValidWhenNotRevokedAndNotExpired() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, ttl);
        assertTrue(refreshToken.isValid());
    }

    @Test
    void shouldNotBeValidWhenRevoked() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, ttl);
        refreshToken.revoke();
        assertFalse(refreshToken.isValid());
    }

    @Test
    void shouldNotBeValidWhenExpired() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, Duration.ofMillis(1));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertFalse(refreshToken.isValid());
    }

    @Test
    void shouldRevokeToken() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, ttl);
        refreshToken.revoke();

        assertTrue(refreshToken.isRevoked());
        assertNotNull(refreshToken.getRevokedAt());
        assertTrue(refreshToken.getRevokedAt().isBefore(Instant.now().plusMillis(100)));
    }

    @Test
    void shouldDetectExpiredToken() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, Duration.ofMillis(1));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue(refreshToken.isExpired());
    }

    @Test
    void shouldNotDetectExpiredWhenNotExpired() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, ttl);
        assertFalse(refreshToken.isExpired());
    }

    @Test
    void shouldReturnRemainingTime() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, ttl);
        Duration remaining = refreshToken.getRemainingTime();
        assertTrue(remaining.compareTo(ttl) <= 0);
    }

    @Test
    void shouldReturnNegativeRemainingTimeWhenExpired() {
        RefreshToken refreshToken = RefreshToken.create(userId, email, role, Duration.ofMillis(1));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Duration remaining = refreshToken.getRemainingTime();
        assertTrue(remaining.isNegative());
    }
}
