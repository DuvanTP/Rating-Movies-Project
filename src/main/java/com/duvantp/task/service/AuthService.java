package com.duvantp.task.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.duvantp.task.Auth.AuthResponse;
import com.duvantp.task.Auth.LoginRequest;
import com.duvantp.task.Auth.RegisterRequest;
import com.duvantp.task.models.Role;
import com.duvantp.task.models.User;
import com.duvantp.task.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder; 

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword())) 
        .firtname(request.getFirtname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .role(Role.USER)
        .build();

        userRepository.save(user);

        return AuthResponse.builder().token(jwtService.getToken(user)).build();
        
    }

}
