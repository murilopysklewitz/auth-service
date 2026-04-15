package com.microsservice.auth.infra.api.authController;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenWebRequest(
        @NotBlank String userId,
        @NotBlank String tokenId
) {}
