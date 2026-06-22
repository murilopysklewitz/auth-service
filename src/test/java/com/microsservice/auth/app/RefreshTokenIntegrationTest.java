package com.microsservice.auth.app;


import com.microsservice.auth.app.refreshTokenUseCase.RefreshTokenUseCase;
import com.microsservice.auth.domain.ports.RefreshTokenRepository;
import com.microsservice.auth.infra.cache.RedisService;
import com.redis.testcontainers.RedisContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest
@Testcontainers
public class RefreshTokenIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest");

    @Container
    @ServiceConnection
    static RedisContainer redisContainer = new RedisContainer("redis:latest");

    @Autowired
    private RefreshTokenUseCase refreshTokenUseCase;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RedisService redisService;



}
