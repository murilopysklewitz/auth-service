package com.microsservice.auth.app;

import com.microsservice.auth.domain.User;

public class UserMapper {

    public static User toUser(RegisterUserRequest request, String hashedPassword) {
        return User.create(request.getEmail(), hashedPassword, request.getRole());
    }

    public static RegisterUserResponse toResponse(User user) {
        return new RegisterUserResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
