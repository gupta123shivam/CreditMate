package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionResponseDto doTransaction(TransactionRequestDto transactionRequestDto);

    Optional<TransactionResponseDto> getTransactionById(Long id);

    List<TransactionResponseDto> getTransactionsByCardNumber(String cardNumber);
}
