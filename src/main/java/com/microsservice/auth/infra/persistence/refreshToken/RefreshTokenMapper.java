package com.microsservice.auth.infra.persistence.refreshToken;

import com.microsservice.auth.domain.RefreshToken;

public class RefreshTokenMapper {
    public RefreshToken toDomain(RefreshTokenJpaEntity entity){
        RefreshToken domain = RefreshToken.restore(
                entity.getTokenId(),
                entity.getUserId(),
                entity.getRole(),
                entity.getExpiresAt(),
                entity.getCreatedAt(),
                entity.isRevoked(),
                entity.getRevokedAt()
        );
        return domain;
    }

    public RefreshTokenJpaEntity toEntity (RefreshToken domain){
        RefreshTokenJpaEntity entity =  new RefreshTokenJpaEntity(
                domain.getTokenId(),
                domain.getUserId(),
                domain.getRole(),
                domain.getExpiresAt(),
                domain.getCreatedAt(),
                domain.isRevoked(),
                domain.getRevokedAt()
        );
        return entity;
    }
}
