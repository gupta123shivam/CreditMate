package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.UserDetailsDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserController {
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid);

    public ResponseEntity<UserDetailsDto> getCurrentUser();
}
