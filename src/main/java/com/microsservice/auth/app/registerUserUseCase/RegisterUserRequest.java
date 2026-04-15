package com.microsservice.auth.app.registerUserUseCase;

import com.microsservice.auth.domain.UserRole;

public class RegisterUserRequest {
    private final String email;
    private final String password;
    private final UserRole role;

    public RegisterUserRequest(String email, String password, UserRole role) {
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
