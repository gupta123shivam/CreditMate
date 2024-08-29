package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.StatementRequestDto;
import com.shivam.CreditMate.dto.response.StatementResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller interface for managing credit card statements.
 * Provides endpoints for generating and retrieving statements.
 */
@PreAuthorize("isAuthenticated() and hasRole('OWNER')")
@RequestMapping("/api/statements")
public interface StatementController {

    /**
     * Generates a statement for a specified credit card.
     *
     * @param statementRequestDto the details required to generate the statement
     * @return a ResponseEntity containing the generated statement details
     */
    @PostMapping("/{cardNumber}")
    ResponseEntity<StatementResponseDto> generateStatement(@Valid @RequestBody StatementRequestDto statementRequestDto);

    /**
     * Retrieves a statement by its UUID.
     *
     * @param statementUuid the UUID of the statement to be retrieved
     * @return a ResponseEntity containing the statement details
     */
    @GetMapping("/{statementUuid}")
    ResponseEntity<StatementResponseDto> getStatementByUuid(@PathVariable String statementUuid);

    /**
     * Retrieves the latest statement for a specified credit card.
     *
     * @param cardNumber the number of the credit card for which to retrieve the latest statement
     * @return a ResponseEntity containing the latest statement details
     */
    @GetMapping("/latest/{cardNumber}")
    ResponseEntity<StatementResponseDto> getLatestStatement(@PathVariable String cardNumber);
}
