package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.AdminController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminControllerimpl implements AdminController {

    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @Override
    @GetMapping("/test")
    public String testAdmin() {
        return "This must be only accesaable to ADMIN";
    }
}
