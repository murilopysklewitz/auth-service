package com.microsservice.auth.infra.api.authController;

import com.microsservice.auth.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class RegisterUserWebRequest {
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String password;

    public RegisterUserWebRequest(String email, String password) {
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
