package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<UserDetailsDto> findByUuid(String uuid);
}
