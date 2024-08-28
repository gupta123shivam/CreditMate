package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.dto.request.UserUpdateRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
public interface UserController {
    // Authenticated user only access
    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getUserProfile();

    @PutMapping("/profile")
    public ResponseEntity<UserDetailsDto> updateUserProfile(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto);
}
