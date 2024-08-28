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

import java.security.Principal;

@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto);

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> loginUser(LoginRequestDto loginRequestDto);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    ResponseEntity<String> logoutUser(@AuthenticationPrincipal User userDetails);
}
