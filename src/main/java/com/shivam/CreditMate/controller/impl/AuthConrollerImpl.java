package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.AuthController;
import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.exception.exceptions.AuthException.*;
import com.shivam.CreditMate.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthConrollerImpl implements AuthController {
    private final AuthService authService;

    @Autowired
    public AuthConrollerImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        log.info("Entering register user: " + registerRequestDto);
        try {
            RegisterResponseDto registerResponse = authService.registerUser(registerRequestDto);

            // Return response entity with CREATED status
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create("/api/users/" + registerResponse.getUuid())); // Assuming you have a method to get the UUID

            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(registerResponse);
        } catch (EmailAlreadyExistsException e) {
            throw e;  // Re-throwing the exception to be handled by the global exception handler
        } catch (Exception e) {
            // Optionally log the exception or perform other logic
            throw e;  // Re-throwing the exception to be handled by the global exception handler
        }
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("Entering loginUser in AuthController" + loginRequestDto);
        try {
            LoginResponseDto loginResponse = authService.loginUser(loginRequestDto);

            return ResponseEntity.ok(loginResponse);
        } catch (InvalidCredentialsException | UserNotFoundException e) {
            throw e;  // Re-throwing the exception to be handled by the global exception handler
        } catch (Exception e) {
            // Optionally log the exception or perform other logic
            throw e;  // Re-throwing the exception to be handled by the global exception handler
        }
    }
}
