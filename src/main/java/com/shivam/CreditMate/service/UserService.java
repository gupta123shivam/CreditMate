package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.UserDto;
import com.shivam.CreditMate.model.User;

import java.util.UUID;

public interface UserService {
    User registerUser(UserDto userDto);
    User loadUserByUsername(String username);
    User findByUuid(UUID uuid);
    User findById(Long id);
}
