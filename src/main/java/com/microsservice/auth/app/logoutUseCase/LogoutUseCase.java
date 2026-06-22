package com.microsservice.auth.app.logoutUseCase;

import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.ports.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase {
    private final RefreshTokenRepository refreshTokenRepository;

    public LogoutUseCase(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void execute(LogoutRequest request){
        RefreshToken refreshToken = refreshTokenRepository.findById(request.refreshTokenId());
        refreshToken.revoke();
        refreshTokenRepository.save(refreshToken);

    }
}
