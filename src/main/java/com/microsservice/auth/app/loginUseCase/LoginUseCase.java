package com.microsservice.auth.app.loginUseCase;

import com.microsservice.auth.domain.*;
import com.microsservice.auth.domain.exceptions.InvalidCredentialsException;
import com.microsservice.auth.domain.exceptions.UserBlockedException;
import com.microsservice.auth.domain.exceptions.UserInactiveException;
import com.microsservice.auth.domain.ports.JwtService;
import com.microsservice.auth.domain.ports.PasswordHasher;
import com.microsservice.auth.domain.ports.RefreshTokenRepository;
import com.microsservice.auth.domain.ports.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenStore;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public LoginUseCase(UserRepository userRepository,
                        PasswordHasher passwordHasher,
                        JwtService jwtService,
                        RefreshTokenRepository refreshTokenStore) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
        this.refreshTokenStore = refreshTokenStore;
    }

    public LoginResponse execute(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActivate()) throw new UserInactiveException("User is inactive");
        if (user.isBlocked()) throw new UserBlockedException("User is blocked");

        if (!passwordHasher.verify(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateToken(
                user.getId().toString(),
                user.getRole().name()
        );

        RefreshToken refreshToken = RefreshToken.create(
                user.getId().toString(),
                user.getRole().name(),
                Duration.ofMillis(refreshExpiration)
        );
        refreshTokenStore.save(refreshToken);

        return new LoginResponse(accessToken, refreshToken.getTokenId(), user.getId().toString(), user.getRole().name());
    }
}
