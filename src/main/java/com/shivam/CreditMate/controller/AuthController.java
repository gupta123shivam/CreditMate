package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller interface for authentication-related operations.
 * Provides endpoints for user registration, login, and logout.
 */
@RequestMapping("/api/auth")
public interface AuthController {

    /**
     * Registers a new user.
     *
     * @param registerRequestDto the registration details of the new user
     * @return a ResponseEntity containing the registration response
     */
    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto);

    /**
     * Logs in an existing user. Also attached JWT token to HTTP headers.
     *
     * @param loginRequestDto the login details of the user
     * @return a ResponseEntity containing the login response
     */
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> loginUser(LoginRequestDto loginRequestDto);

    /**
     * Logs out the currently authenticated user.
     * Accessible only by authenticated users.
     *
     * @param userDetails the details of the authenticated user injected by Spring
     * @return a ResponseEntity containing a logout confirmation message
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    ResponseEntity<String> logoutUser(@AuthenticationPrincipal User userDetails);
}
