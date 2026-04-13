package com.microsservice.auth.app;

import com.microsservice.auth.domain.UserRole;

import java.util.UUID;

public record RegisterUserResponse(UUID userId, String email, UserRole role) {
}
