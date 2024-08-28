package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.TransactionController;
import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TransactionControllerImpl implements TransactionController {
    @Override
    public ResponseEntity<TransactionResponseDto> createTransaction(TransactionRequestDto transactionRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<TransactionResponseDto> getTransactionById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsByCardNumber(String cardNumber) {
        return null;
    }

    @Override
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions() {
        return null;
    }
}
