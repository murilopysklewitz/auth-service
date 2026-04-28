package com.microsservice.auth.app.refreshTokenUseCase;


import com.microsservice.auth.domain.RefreshToken;
import com.microsservice.auth.domain.exceptions.InvalidRefreshTokenException;
import com.microsservice.auth.domain.ports.JwtService;
import com.microsservice.auth.domain.ports.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
public class RefreshTokenUseCase {
    private RefreshTokenRepository refreshTokenRepository;
    private JwtService jwtService;

    public RefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }


    @Transactional
    public RefreshTokenResponse execute(RefreshTokenRequest request){
        RefreshToken token = refreshTokenRepository.findByTokenId(UUID.fromString(request.getRefreshToken()));

        if(!token.isValid()){
            throw new InvalidRefreshTokenException();
        }
        token.revoke();
        refreshTokenRepository.save(token);

        String newAccessToken = jwtService.generateToken(
                token.getUserId(),
                token.getRole()
        );
        RefreshToken newRefreshToken = RefreshToken.create(
                token.getUserId(),
                token.getRole(),
                Duration.ofDays(7)
        );
        refreshTokenRepository.save(newRefreshToken);
        return new RefreshTokenResponse(newAccessToken, newRefreshToken.getTokenId().toString());

    }

}
