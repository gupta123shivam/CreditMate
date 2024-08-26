package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.model.User;

public interface UserService {
    RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
    User findByUuid(String uuid);
}
