package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) {
        return null;
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return null;
    }

    @Override
    public User findByUuid(String uuid) {
        return null;
    }
}
