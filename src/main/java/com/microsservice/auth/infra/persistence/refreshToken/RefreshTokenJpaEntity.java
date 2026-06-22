package com.microsservice.auth.infra.persistence.refreshToken;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_token", indexes = {
        @Index(name = "idx_refresh_token_user_id", columnList = "user_id"),
        @Index(name = "idx_refresh_token_token_id", columnList = "id"),
        @Index(name = "idx_refresh_token_expires_at", columnList = "expires_at"),
        @Index(name = "idx_refresh_token_created_at", columnList = "created_at")
})
public class RefreshTokenJpaEntity {

    @Column(name = "id", nullable = false, unique = true)
    @Id
    private UUID id;
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

    public RefreshTokenJpaEntity(UUID id, UUID userId, String role, Instant expiresAt, Instant createdAt, boolean revoked, Instant revokedAt) {
        this.id = id;
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

    public UUID getId() {
        return id;
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
