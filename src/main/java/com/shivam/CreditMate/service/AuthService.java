package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;

public interface AuthService {
    RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto);

    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
}
