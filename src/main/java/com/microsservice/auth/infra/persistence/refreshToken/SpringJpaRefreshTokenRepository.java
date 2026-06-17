package com.microsservice.auth.infra.persistence.refreshToken;

import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SpringJpaRefreshTokenRepository extends JpaRepository<RefreshTokenJpaEntity, UUID> {
    @Query("UPDATE RefreshTokenJpaEntity r SET r.revoked = true WHERE r.userId = :userId")
    @Modifying
    void revokeTokensForUser(UUID userId);
    void delete(UUID refreshTokenId);
    @Query("DELETE FROM RefreshTokenJpaEntity r WHERE r.expiresAt < CURRENT_TIMESTAMP")
    @Modifying
    void deleteExpiredTokens();
    RefreshToken findByToken(UUID token);

}
