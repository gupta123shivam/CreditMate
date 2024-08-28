package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.HealthController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthControllerImpl implements HealthController {
    @Override
    public String checkHealth() {
        return "Service is UP and running.";
    }
}
