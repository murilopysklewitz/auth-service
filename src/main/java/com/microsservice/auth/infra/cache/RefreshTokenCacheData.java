package com.microsservice.auth.infra.cache;

import java.time.Instant;
import java.util.UUID;

public record RefreshTokenCacheData(UUID userId, String role, Instant expiresAt) {

}
