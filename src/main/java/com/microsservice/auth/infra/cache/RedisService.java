package com.microsservice.auth.infra.cache;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public interface RedisService {

    void saveRefreshToken(UUID tokenId, RefreshTokenCacheData data, Duration ttl);

    Optional<RefreshTokenCacheData> getRefreshToken(UUID tokenId);

    void deleteRefreshToken(UUID tokenId);
}