package com.microsservice.auth.infra.persistence.refreshToken;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_token", indexes = {
        @Index(name = "idx_refresh_token_user_id", columnList = "userId"),
        @Index(name = "idx_refresh_token_token_id", columnList = "tokenId"),
        @Index(name = "idx_refresh_token_expires_at", columnList = "expiresAt"),
        @Index(name = "idx_refresh_token_created_at", columnList = "createdAt")
})
public class RefreshTokenJpaEntity {

    @Column(name = "token_id", nullable = false, unique = true)
    @Id
    private UUID tokenId;
    @Column(name = "user_id", nullable = false)
    private  UUID userId;
    @Column(name = "role", nullable = false)
    private  String role;

    @Column(name = "expires_at", nullable = false)
    private  Instant expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private  Instant createdAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;
    @Column(name = "revoked_at")
    private Instant revokedAt;

    public RefreshTokenJpaEntity(UUID tokenId, UUID userId, String role, Instant expiresAt, Instant createdAt, boolean revoked, Instant revokedAt) {
        this.tokenId = tokenId;
        this.userId = userId;
        this.role = role;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revoked = revoked;
        this.revokedAt = revokedAt;
    }

    public RefreshTokenJpaEntity() {
    }


    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public UUID getUserId() {
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

    public boolean isRevoked() {
        return revoked;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }
}
