package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.StatementController;
import com.shivam.CreditMate.dto.request.StatementRequestDto;
import com.shivam.CreditMate.dto.response.StatementResponseDto;
import com.shivam.CreditMate.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatementControllerImpl implements StatementController {

    private final StatementService statementService;

    @Autowired
    public StatementControllerImpl(StatementService statementService) {
        this.statementService = statementService;
    }

    @Override
    public ResponseEntity<StatementResponseDto> generateStatement(StatementRequestDto statementRequestDto) {
        StatementResponseDto responseDto = statementService.generateStatement(statementRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<StatementResponseDto> getStatementByUuid(String statementUuid) {
        StatementResponseDto statement = statementService.getStatementByUuid(statementUuid);
        return ResponseEntity.ok(statement);
    }

    @Override
    public ResponseEntity<StatementResponseDto> getLatestStatement(String cardNumber) {
        StatementResponseDto latestStatement = statementService.getLatestStatement(cardNumber);
        return ResponseEntity.ok(latestStatement);
    }
}
