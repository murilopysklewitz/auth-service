package com.microsservice.auth.infra.api.authController;

import jakarta.validation.constraints.NotBlank;

public record LogoutWebRequest(@NotBlank String refreshTokenId) {}
