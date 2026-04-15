package com.microsservice.auth.app.loginUseCase;

public record LoginResponse(String accessToken, String refreshToken, String userId, String role) {
}
