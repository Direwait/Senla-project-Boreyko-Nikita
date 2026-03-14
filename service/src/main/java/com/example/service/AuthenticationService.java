package com.example.service;

import com.example.model.User;
import com.example.model.enums.Role;
import com.example.repository.UserRepository;
import com.example.security.dto.AuthenticationRequest;
import com.example.security.dto.AuthenticationResponse;
import com.example.security.dto.RegisterRequest;
import com.example.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userUsername(request.getUsername())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .userRegistrationDate(new Date())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        log.info("User registered successfully || username: {} || role: {}", user.getUserUsername(), user.getRole());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = User.builder()
                .userUsername(request.getUsername())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .userRegistrationDate(new Date())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        log.info("Admin registered successfully || username: {} || role: {}", user.getUserUsername(), user.getRole());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUserUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        log.info("User authenticated successfully || username: {} || role: {}", user.getUserUsername(), user.getRole());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
