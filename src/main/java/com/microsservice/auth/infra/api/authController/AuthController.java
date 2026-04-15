package com.microsservice.auth.infra.api.authController;

import com.microsservice.auth.app.loginUseCase.LoginRequest;
import com.microsservice.auth.app.loginUseCase.LoginResponse;
import com.microsservice.auth.app.loginUseCase.LoginUseCase;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserRequest;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserResponse;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginWebRequest request){
        LoginRequest loginRequest = new LoginRequest(request.getEmail(), request.getPassword());
        loginUseCase.execute(loginRequest);
        return ResponseEntity.status(200).build();
    }
}
