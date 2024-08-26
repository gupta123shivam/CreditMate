package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserControllerImpl {

    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto registerRequestDto) {
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        // Implement JWT authentication and return JWT token
        return null;
    }
}
