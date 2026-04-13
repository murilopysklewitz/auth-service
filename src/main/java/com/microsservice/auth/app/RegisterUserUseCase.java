package com.microsservice.auth.app;

import com.microsservice.auth.domain.JwtService;
import com.microsservice.auth.domain.PasswordHasher;
import com.microsservice.auth.domain.User;
import com.microsservice.auth.domain.UserRepository;
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
