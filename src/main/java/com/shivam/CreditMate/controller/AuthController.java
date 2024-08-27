package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;

public interface AuthController {
    ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto);

    ResponseEntity<LoginResponseDto> loginUser(LoginRequestDto loginRequestDto);

    ResponseEntity<String> logoutUser(@AuthenticationPrincipal User userDetails);
}
