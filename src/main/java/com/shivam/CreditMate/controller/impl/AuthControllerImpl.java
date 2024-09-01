package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.AuthController;
import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.service.AuthService;
import com.shivam.CreditMate.utils.UserUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Implementation of {@link AuthController} for handling authentication requests.
 */
@Slf4j
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    /**
     * Constructs an instance of {@link AuthControllerImpl}.
     *
     * @param authService the authentication service
     */
    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     *
     * @param registerRequestDto the registration request
     * @return a {@link ResponseEntity} with the registration response and HTTP status 201 (Created)
     */
    @Override
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        log.info("Registering user: {}", registerRequestDto);
        RegisterResponseDto registerResponse = authService.registerUser(registerRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/users/" + registerResponse.getUuid()));

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(registerResponse);
    }

    /**
     * Logs in a user.
     *
     * @param loginRequestDto the login request
     * @return a {@link ResponseEntity} with the login response and JWT token
     */
    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("Logging in user: {}", loginRequestDto);
        LoginResponseDto loginResponse = authService.loginUser(loginRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + loginResponse.getJwtToken());

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginResponse);
    }

    /**
     * Logs out a user.
     *
     * @param user the authenticated user
     * @return a {@link ResponseEntity} confirming the logout
     */
    @Override
    public ResponseEntity<String> logoutUser(@AuthenticationPrincipal User user) {
        User currentUser = UserUtil.getLoggedInUser();
        return ResponseEntity.ok(authService.logoutUser(currentUser));
    }
}
