package com.microsservice.auth.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.UUID;

public class RefreshToken {

    private final String tokenId;
    private final String userId;
    private final String role;

    private final Instant expiresAt;
    private final Instant createdAt;
    private boolean revoked;
    private Instant revokedAt;

    public RefreshToken(String tokenId, String userId, String role, Instant expiresAt, Instant createdAt, boolean revoked, Instant revokedAt) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.role = role;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revoked = revoked;
        this.revokedAt = revokedAt;
    }

    public static RefreshToken create(String userId, String role, Duration ttl) {
        if(userId == null || userId.isBlank()) throw new IllegalArgumentException("userId cannot be null or blank");
        if(role == null || role.isBlank()) throw new IllegalArgumentException("role cannot be null or blank");
        if(ttl == null || ttl.isNegative() || ttl.isZero()) throw new IllegalArgumentException("ttl must be a positive duration");

        return new RefreshToken(UUID.randomUUID().toString(), userId, role, Instant.now().plus(ttl), Instant.now(), false, null);
    }
    public static RefreshToken restore(String tokenId, String userId, String role, Instant expiresAt, Instant createdAt, boolean revoked, Instant revokedAt) {
        return new RefreshToken(tokenId, userId, role, expiresAt, createdAt, revoked, revokedAt);
    }

    public boolean isValid() {
        return !revoked&& LocalDateTime.now().isBefore(ChronoLocalDateTime.from(expiresAt));
    }

    public  void revoke(){
        this.revoked = true;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isRevoked() {
        return revoked;
    }

    public Duration getRemainingTime() {
        return Duration.between(Instant.now(), expiresAt);
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }
}
