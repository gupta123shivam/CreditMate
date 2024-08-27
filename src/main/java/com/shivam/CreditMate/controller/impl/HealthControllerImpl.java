package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.HealthController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthControllerImpl implements HealthController {
    @Override
    @GetMapping
    public String checkHealth(){
        return "Service is UP and running.";
    }
}
