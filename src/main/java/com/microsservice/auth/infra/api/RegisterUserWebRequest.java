package com.microsservice.auth.infra.api;

import com.microsservice.auth.domain.UserRole;
import jakarta.validation.constraints.NotNull;

public class RegisterUserWebRequest {
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    @NotNull
    private final UserRole role;

    public RegisterUserWebRequest(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
