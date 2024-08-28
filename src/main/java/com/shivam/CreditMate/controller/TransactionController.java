package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/transactions")
public interface TransactionController {
    @PostMapping
    ResponseEntity<TransactionResponseDto> createTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto);

    @GetMapping("/{id}")
    ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long id);

    @GetMapping("/card/{cardNumber}")
    ResponseEntity<List<TransactionResponseDto>> getTransactionsByCardNumber(@PathVariable String cardNumber);

    @GetMapping("/user")
    ResponseEntity<List<List<TransactionResponseDto>>> getAllTransactions(@RequestParam Long limit);
}
