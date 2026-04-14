package com.microsservice.auth.infra.api;

import com.microsservice.auth.app.RegisterUserRequest;
import com.microsservice.auth.app.RegisterUserResponse;
import com.microsservice.auth.app.RegisterUserUseCase;
import com.microsservice.auth.domain.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public RegisterUserResponse registerUser(@RequestBody @Valid RegisterUserWebRequest request) {
        RegisterUserRequest request1 = new RegisterUserRequest(
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        RegisterUserResponse response = registerUserUseCase.execute(request1);
        return response;
    }
}
