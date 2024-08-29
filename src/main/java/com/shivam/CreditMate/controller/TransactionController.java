package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller interface for managing credit card transactions.
 * Provides endpoints for creating and retrieving transactions.
 */
@RequestMapping("/api/transactions")
public interface TransactionController {

    /**
     * Creates a new transaction.
     *
     * @param transactionRequestDto the details of the transaction to be created
     * @return a ResponseEntity containing the created transaction details
     */
    @PostMapping
    ResponseEntity<TransactionResponseDto> createTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto);

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id the ID of the transaction to be retrieved
     * @return a ResponseEntity containing the transaction details
     */
    @GetMapping("/{id}")
    ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long id);

    /**
     * Retrieves transactions for a specific credit card.
     *
     * @param cardNumber the number of the credit card for which to retrieve transactions
     * @return a ResponseEntity containing a list of transaction details
     */
    @GetMapping("/card/{cardNumber}")
    ResponseEntity<List<TransactionResponseDto>> getTransactionsByCardNumber(@PathVariable String cardNumber);

    /**
     * Retrieves all transactions for the authenticated user with a limit.
     *
     * @param limit the maximum number of transactions to retrieve
     * @return a ResponseEntity containing a list of lists of transaction details
     */
    @GetMapping("/user")
    ResponseEntity<List<List<TransactionResponseDto>>> getAllTransactions(@RequestParam Long limit);
}
