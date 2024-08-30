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

/**
 * Controller interface for managing user profiles.
 * Provides endpoints for retrieving and updating user profile information.
 */
@PreAuthorize("isAuthenticated() and hasRole('ROLE_OWNER')")
@RequestMapping("/api/users")
public interface UserController {

    /**
     * Retrieves the profile of the currently authenticated user.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @return a ResponseEntity containing the user profile details
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getUserProfile();

    /**
     * Updates the profile of the currently authenticated user.
     *
     * @param userUpdateRequestDto the updated user profile information
     * @return a ResponseEntity containing the updated user profile details
     */
    @PutMapping("/profile")
    public ResponseEntity<UserDetailsDto> updateUserProfile(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto);
}
