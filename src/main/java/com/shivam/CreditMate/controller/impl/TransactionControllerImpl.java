package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.TransactionController;
import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import com.shivam.CreditMate.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementation of {@link TransactionController} for handling transaction-related operations.
 */
@RestController
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;

    /**
     * Constructs an instance of {@link TransactionControllerImpl}.
     *
     * @param transactionService the service to manage transaction-related operations
     */
    @Autowired
    public TransactionControllerImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Creates a new transaction based on the provided request details.
     *
     * @param transactionRequestDto the details for creating a new transaction
     * @return a {@link ResponseEntity} containing the created transaction response and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<TransactionResponseDto> createTransaction(TransactionRequestDto transactionRequestDto) {
        TransactionResponseDto responseDto = transactionService.doTransaction(transactionRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id the ID of the transaction to retrieve
     * @return a {@link ResponseEntity} containing the transaction details and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<TransactionResponseDto> getTransactionById(Long id) {
        TransactionResponseDto transactionById = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionById);
    }

    /**
     * Retrieves a list of transactions by card number.
     *
     * @param cardNumber the card number to fetch transactions for
     * @return a {@link ResponseEntity} containing the list of transactions and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByCardNumber(String cardNumber) {
        List<TransactionResponseDto> transactions = transactionService.getTransactionsByCardNumber(cardNumber);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Retrieves all transactions with an optional limit.
     *
     * @param limit the maximum number of transactions to retrieve (optional)
     * @return a {@link ResponseEntity} containing the list of transactions grouped in lists and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<List<List<TransactionResponseDto>>> getAllTransactions(Long limit) {
        List<List<TransactionResponseDto>> transactions = transactionService.getAllTransactions(limit);
        return ResponseEntity.ok(transactions);
    }
}
