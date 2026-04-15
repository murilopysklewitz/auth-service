package com.microsservice.auth.app.registerUserUseCase;

import com.microsservice.auth.domain.UserRole;

import java.util.UUID;

public record RegisterUserResponse(UUID userId, String email, UserRole role) {
}
