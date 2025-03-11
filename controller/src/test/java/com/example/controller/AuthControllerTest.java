package com.example.controller;

import com.example.dto.security.AuthenticationRequest;
import com.example.dto.security.AuthenticationResponse;
import com.example.dto.security.RegisterRequest;
import com.example.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }

    @Test
    void registerAdmin() {
        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        when(authenticationService.registerAdmin(any(RegisterRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authController.registerAdmin(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }

    @Test
    void authenticate() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authController.authenticate(authenticationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }
}
