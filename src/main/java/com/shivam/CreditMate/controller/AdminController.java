package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.UserDetailsDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller interface for administrative operations.
 * All methods in this controller are accessible only to users with ADMIN role.
 */
@RequestMapping("/api/admin")
public interface AdminController {

    /**
     * Test endpoint for ADMIN users.
     * Accessible only by authenticated users with ADMIN role.
     *
     * @return a test string for ADMIN users
     */
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @GetMapping("/test")
    String testAdmin();

    /**
     * Retrieves user details by UUID.
     * Accessible only by users with ADMIN role.
     *
     * @param uuid the UUID of the user
     * @return a ResponseEntity containing user details
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{_uuid}")
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid);
}
