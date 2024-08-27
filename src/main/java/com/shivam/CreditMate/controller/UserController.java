package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<UserDetailsDto> getUser(String uuid);
    ResponseEntity<UserDetailsDto> getCurrentUser();
}
