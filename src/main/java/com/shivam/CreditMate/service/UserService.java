package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsDto findByUuid(String uuid);

    User loadUserByEmail(String email);

    User loadUserByUsername(String username);
}
