package com.microsservice.auth.infra.api.authController;

import jakarta.validation.constraints.NotNull;

public record LogoutAllWebRequest(@NotNull String userId) {
}
