package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.TransactionController;
import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import com.shivam.CreditMate.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionControllerImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<TransactionResponseDto> createTransaction(TransactionRequestDto transactionRequestDto) {
        TransactionResponseDto responseDto = transactionService.doTransaction(transactionRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<TransactionResponseDto> getTransactionById(Long id) {
        TransactionResponseDto transactionById = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionById);
    }

    @Override
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByCardNumber(String cardNumber) {
        List<TransactionResponseDto> transactions = transactionService.getTransactionsByCardNumber(cardNumber);
        return ResponseEntity.ok(transactions);
    }

    @Override
    public ResponseEntity<List<List<TransactionResponseDto>>> getAllTransactions(Long limit) {
        List<List<TransactionResponseDto>> transactions = transactionService.getAllTransactions(limit);
        return ResponseEntity.ok(transactions);
    }
}
