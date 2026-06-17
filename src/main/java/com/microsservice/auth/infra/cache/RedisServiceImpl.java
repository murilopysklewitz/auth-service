package com.microsservice.auth.infra.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, RefreshTokenCacheData> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, RefreshTokenCacheData> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    private String getKey(UUID tokenId) {

        return "refresh_token:" + tokenId.toString();
    }

    @Override
    public void saveRefreshToken(UUID tokenId, RefreshTokenCacheData cacheData, Duration ttl) {
        redisTemplate.opsForValue().set(getKey(tokenId), cacheData, ttl);
    }

    @Override
    public Optional<RefreshTokenCacheData> getRefreshToken(UUID tokenId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getKey(tokenId)));
    }

    @Override
    public void deleteRefreshToken(UUID tokenId) {
        redisTemplate.delete(getKey(tokenId));
    }
}
