package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto doTransaction(TransactionRequestDto transactionRequestDto);

    TransactionResponseDto getTransactionById(Long id);

    List<TransactionResponseDto> getTransactionsByCardNumber(String cardNumber);

    List<List<TransactionResponseDto>> getAllTransactions(Long limit);
}
