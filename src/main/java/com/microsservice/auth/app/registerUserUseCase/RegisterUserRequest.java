package com.microsservice.auth.app.registerUserUseCase;

import com.microsservice.auth.domain.UserRole;

public class RegisterUserRequest {
    private final String email;
    private final String password;

    public RegisterUserRequest(String email, String password) {
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
