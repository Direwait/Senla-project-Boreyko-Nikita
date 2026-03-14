package com.example.service;

import com.example.model.User;
import com.example.model.enums.Role;
import com.example.repository.UserRepository;
import com.example.security.dto.AuthenticationRequest;
import com.example.security.dto.AuthenticationResponse;
import com.example.security.dto.RegisterRequest;
import com.example.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private final String USERNAME = "testUser";
    private final String PASSWORD = "testPass123";
    private final String ENCODED_PASS = "encodedPass123";
    private final String JWT_TOKEN = "test.jwt.token";

    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest(USERNAME, PASSWORD);

        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASS);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jwtService.generateToken(any(User.class))).thenReturn(JWT_TOKEN);

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals(JWT_TOKEN, response.getToken());

        verify(passwordEncoder).encode(PASSWORD);
        verify(userRepository).save(argThat(user ->
                user.getUserUsername().equals(USERNAME) &&
                        user.getUserPassword().equals(ENCODED_PASS) &&
                        user.getRole() == Role.USER
        ));
        verify(jwtService).generateToken(any(User.class));
    }


    @Test
    void testRegisterAdmin() {
        RegisterRequest request = new RegisterRequest(USERNAME, PASSWORD);

        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASS);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));
        when(jwtService.generateToken(any(User.class))).thenReturn(JWT_TOKEN);

        AuthenticationResponse response = authenticationService.registerAdmin(request);

        assertNotNull(response);
        assertEquals(JWT_TOKEN, response.getToken());

        verify(userRepository).save(argThat(user -> user.getRole() == Role.ADMIN));
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest request = new AuthenticationRequest(USERNAME, PASSWORD);
        User user = User.builder()
                .userUsername(USERNAME)
                .userPassword(ENCODED_PASS)
                .build();

        when(userRepository.findByUserUsername(USERNAME)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(JWT_TOKEN);

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertEquals(JWT_TOKEN, response.getToken());

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD));
        verify(userRepository).findByUserUsername(USERNAME);
        verify(jwtService).generateToken(user);
    }
}
