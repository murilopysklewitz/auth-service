package com.microsservice.auth.app;

import com.microsservice.auth.domain.ports.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogoutAllUseCase {
    private final RefreshTokenRepository refreshTokenRepository;

    public LogoutAllUseCase(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void execute(LogoutAllRequest request){
        refreshTokenRepository.revokeTokensForUser(UUID.fromString(request.userId()));
    }
}
