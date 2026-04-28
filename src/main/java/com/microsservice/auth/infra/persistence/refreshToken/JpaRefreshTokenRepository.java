package com.microsservice.auth.infra.persistence.refreshToken;


import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.ports.RefreshTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaRefreshTokenRepository implements RefreshTokenRepository {
    private final SpringJpaRefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    public JpaRefreshTokenRepository(SpringJpaRefreshTokenRepository repository, RefreshTokenMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenJpaEntity entity = mapper.toEntity(refreshToken);
        RefreshTokenJpaEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public RefreshToken findByTokenId(UUID tokenId) {
        Optional<RefreshToken> entity = repository.findById(tokenId).map(mapper::toDomain);
        if (entity.isEmpty()) {
            throw new RuntimeException("Refresh token not found");
        }
        return entity.get();
    }

    @Override
    public void delete(UUID refreshTokenId) {
        repository.delete(refreshTokenId);
    }

    @Override
    public void revokeTokensForUser(UUID userId) {
            repository.revokeTokensForUser(userId);
    }

    @Override
    public void deleteExpiredTokens() {
        repository.deleteExpiredTokens();
    }
}
