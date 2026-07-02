    package com.microsservice.auth.app.refreshTokenUseCase;


    import com.microsservice.auth.domain.RefreshToken;
    import com.microsservice.auth.domain.exceptions.InvalidRefreshTokenException;
    import com.microsservice.auth.domain.ports.JwtService;
    import com.microsservice.auth.domain.ports.RefreshTokenRepository;
    import com.microsservice.auth.infra.cache.RedisService;
    import com.microsservice.auth.infra.cache.RefreshTokenCacheData;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.Duration;
    import java.time.Instant;
    import java.util.Optional;
    import java.util.UUID;

    @Service
    public class RefreshTokenUseCase {
        private RefreshTokenRepository refreshTokenRepository;
        private JwtService jwtService;
        private RedisService redisService;

        public RefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository, JwtService jwtService, RedisService redisService) {
            this.refreshTokenRepository = refreshTokenRepository;
            this.jwtService = jwtService;
            this.redisService = redisService;
        }


        @Transactional
        public RefreshTokenResponse execute(RefreshTokenRequest request) {

            UUID tokenId = UUID.fromString(request.getRefreshToken());

            RefreshTokenCacheData data;

            Optional<RefreshTokenCacheData> cache = redisService.getRefreshToken(tokenId);

            RefreshToken token = null;

            if (cache.isPresent()) {

                data = cache.get();

                if (Instant.now().isAfter(data.expiresAt())) {
                    redisService.deleteRefreshToken(tokenId);
                    throw new InvalidRefreshTokenException();
                }

                token = refreshTokenRepository.findById(tokenId);

            } else {

                token = refreshTokenRepository.findById(tokenId);

                if (token == null || !token.isValid()) {
                    throw new InvalidRefreshTokenException();
                }

                data = new RefreshTokenCacheData(
                        token.getUserId(),
                        token.getRole(),
                        token.getExpiresAt()
                );

                redisService.saveRefreshToken(
                        tokenId,
                        data,
                        token.getRemainingTime()
                );
            }

            if (token == null || !token.isValid()) {
                throw new InvalidRefreshTokenException();
            }

            token.revoke();
            refreshTokenRepository.save(token);

            redisService.deleteRefreshToken(tokenId);

            RefreshToken newToken = RefreshToken.create(
                    token.getUserId(),
                    token.getEmail(),
                    token.getRole(),
                    Duration.ofDays(7)
            );

            refreshTokenRepository.save(newToken);

            RefreshTokenCacheData newCache = new RefreshTokenCacheData(
                    newToken.getUserId(),
                    newToken.getRole(),
                    newToken.getExpiresAt()
            );

            redisService.saveRefreshToken(
                    newToken.getId(),
                    newCache,
                    newToken.getRemainingTime()
            );

            String accessToken = jwtService.generateToken(
                    newToken.getUserId(),
                    newToken.getEmail(),
                    newToken.getRole()
            );

            return new RefreshTokenResponse(
                    accessToken,
                    newToken.getId().toString()
            );
        }

    }
