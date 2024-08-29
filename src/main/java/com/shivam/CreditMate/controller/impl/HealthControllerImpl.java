package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.HealthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of {@link HealthController} for providing health check functionality.
 */
@RestController
public class HealthControllerImpl implements HealthController {

    /**
     * Checks the health status of the service.
     *
     * @return a {@link ResponseEntity} with the status message "Service is UP and running."
     * and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<String> checkHealth() {
        String healthStatus = "Service is UP and running.";
        return new ResponseEntity<>(healthStatus, HttpStatus.OK); // HTTP 200 OK
    }
}
