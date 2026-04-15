package com.microsservice.auth.app.registerUserUseCase;

import com.microsservice.auth.domain.exceptions.EmailAlreadyRegisteredException;
import com.microsservice.auth.domain.ports.JwtService;
import com.microsservice.auth.domain.ports.PasswordHasher;
import com.microsservice.auth.domain.User;
import com.microsservice.auth.domain.ports.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public RegisterUserUseCase(UserRepository userRepository, PasswordHasher passwordHasher, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
    }

    public RegisterUserResponse execute(RegisterUserRequest request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new EmailAlreadyRegisteredException(request.getEmail());
        }
        String passwordHash = passwordHasher.hash(request.getPassword());
        User newUser = User.create(request.getEmail(), passwordHash, request.getRole());

        userRepository.save(newUser);
        return new RegisterUserResponse(newUser.getId(), newUser.getEmail(), newUser.getRole());
    }
}
