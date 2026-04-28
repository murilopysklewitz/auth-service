package com.microsservice.auth.infra.api.authController;

import com.microsservice.auth.app.LogoutAllRequest;
import com.microsservice.auth.app.LogoutAllUseCase;
import com.microsservice.auth.app.logoutUseCase.LogoutRequest;
import com.microsservice.auth.app.logoutUseCase.LogoutUseCase;
import com.microsservice.auth.app.refreshTokenUseCase.RefreshTokenRequest;
import com.microsservice.auth.app.refreshTokenUseCase.RefreshTokenResponse;
import com.microsservice.auth.app.refreshTokenUseCase.RefreshTokenUseCase;
import com.microsservice.auth.app.loginUseCase.LoginRequest;
import com.microsservice.auth.app.loginUseCase.LoginResponse;
import com.microsservice.auth.app.loginUseCase.LoginUseCase;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserRequest;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserResponse;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final LogoutAllUseCase logoutAllUseCase;

    public AuthController(LogoutUseCase logoutUseCase,
                          RegisterUserUseCase registerUserUseCase,
                          LoginUseCase loginUseCase,
                          RefreshTokenUseCase refreshTokenUseCase,
                          LogoutAllUseCase logoutAllUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
        this.logoutAllUseCase = logoutAllUseCase;
    }


    @PostMapping("/register")
    public RegisterUserResponse registerUser(@RequestBody @Valid RegisterUserWebRequest request) {
        RegisterUserRequest request1 = new RegisterUserRequest(
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        RegisterUserResponse response = registerUserUseCase.execute(request1);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginWebRequest request){
        LoginRequest loginRequest = new LoginRequest(request.getEmail(), request.getPassword());
        loginUseCase.execute(loginRequest);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenWebRequest webRequest){
        RefreshTokenRequest request = new RefreshTokenRequest(webRequest.refreshToken());
        RefreshTokenResponse response = refreshTokenUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutWebRequest webRequest){
        LogoutRequest request = new LogoutRequest(UUID.fromString(webRequest.refreshTokenId()));
        logoutUseCase.execute(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/logoutUser")
    public ResponseEntity<Void> logoutUser(@RequestBody @Valid LogoutAllWebRequest webRequest){
        LogoutAllRequest request = new LogoutAllRequest(webRequest.userId());
        logoutAllUseCase.execute(request);
        return ResponseEntity.noContent().build();

    }
}
