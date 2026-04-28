package com.microsservice.auth.app.logoutUseCase;

import java.util.UUID;

public record LogoutRequest(UUID refreshTokenId) {
}
