package com.microsservice.auth.app.refreshTokenUseCase;

public class RefreshTokenResponse {
    private final String accessToken;
    private final String refreshTokenId;

    public RefreshTokenResponse(String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.refreshTokenId = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshTokenId() {
        return refreshTokenId;
    }
}
