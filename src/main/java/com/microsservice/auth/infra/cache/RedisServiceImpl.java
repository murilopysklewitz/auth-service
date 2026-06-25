package com.microsservice.auth.infra.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
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
        Object data = redisTemplate.opsForValue().get(getKey(tokenId));
        return Optional.ofNullable((RefreshTokenCacheData) data);
    }

    @Override
    public void deleteRefreshToken(UUID tokenId) {
        redisTemplate.delete(getKey(tokenId));
    }
}
