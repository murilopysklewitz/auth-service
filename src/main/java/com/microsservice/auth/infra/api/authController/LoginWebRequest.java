package com.microsservice.auth.infra.api.authController;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LoginWebRequest {
    @Email
            @NotNull
    String email;
    @NotNull
    String password;

    public LoginWebRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
