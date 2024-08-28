package com.shivam.CreditMate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/health")
public interface HealthController {
    @GetMapping
    String checkHealth();
}
