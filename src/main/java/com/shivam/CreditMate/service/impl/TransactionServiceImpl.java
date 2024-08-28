package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import com.shivam.CreditMate.service.TransactionService;

import java.util.List;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public TransactionResponseDto doTransaction(TransactionRequestDto transactionRequestDto) {
        return null;
    }

    @Override
    public Optional<TransactionResponseDto> getTransactionById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByCardNumber(String cardNumber) {
        return List.of();
    }
}
