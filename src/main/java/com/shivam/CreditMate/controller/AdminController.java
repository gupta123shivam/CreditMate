package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.UserDetailsDto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// Admin only access
@RequestMapping("/api/admin")
public interface AdminController {
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @GetMapping("/test")
    String testAdmin();

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{_uuid}")
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid);
}
