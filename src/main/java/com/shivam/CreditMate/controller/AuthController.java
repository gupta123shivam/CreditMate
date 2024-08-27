package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto);
    ResponseEntity<LoginResponseDto> loginUser(LoginRequestDto loginRequestDto);
}
