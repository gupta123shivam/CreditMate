package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
}
