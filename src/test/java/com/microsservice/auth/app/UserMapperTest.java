package com.microsservice.auth.app;

import com.microsservice.auth.app.registerUserUseCase.RegisterUserRequest;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserResponse;
import com.microsservice.auth.domain.User;
import com.microsservice.auth.domain.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    void shouldMapRegisterUserRequestToUser() {
        RegisterUserRequest request = new RegisterUserRequest("test@example.com", "password", UserRole.USER);
        String hashedPassword = "hashedpassword";

        User user = UserMapper.toUser(request, hashedPassword);

        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        assertEquals(hashedPassword, user.getPasswordHash());
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    void shouldMapUserToRegisterUserResponse() {
        User user = User.create("test@example.com", "hash", UserRole.ADMIN);

        RegisterUserResponse response = UserMapper.toResponse(user);

        assertNotNull(response);
        assertEquals(user.getId(), response.userId());
        assertEquals(user.getEmail(), response.email());
        assertEquals(user.getRole(), response.role());
    }
}
