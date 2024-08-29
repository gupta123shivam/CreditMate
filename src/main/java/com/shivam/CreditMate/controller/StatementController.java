package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.StatementRequestDto;
import com.shivam.CreditMate.dto.response.StatementResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/statements")
public interface StatementController {

    @PostMapping("/{cardNumber}")
    ResponseEntity<StatementResponseDto> generateStatement(@Valid @RequestBody StatementRequestDto statementRequestDto);

    @GetMapping("/{statementUuid}")
    ResponseEntity<StatementResponseDto> getStatementByUuid(@PathVariable String statementUuid);

    @GetMapping("/latest/{cardNumber}")
    ResponseEntity<StatementResponseDto> getLatestStatement(@PathVariable String cardNumber);
}
