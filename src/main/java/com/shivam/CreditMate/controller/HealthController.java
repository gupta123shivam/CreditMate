package com.shivam.CreditMate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller interface for health check operations.
 * Provides an endpoint to check the health status of the application.
 */
@RequestMapping("/health")
public interface HealthController {

    /**
     * Checks the health status of the application.
     */
    @GetMapping
    ResponseEntity<String> checkHealth();
}
