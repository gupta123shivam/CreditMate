package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.StatementController;
import com.shivam.CreditMate.dto.request.StatementRequestDto;
import com.shivam.CreditMate.dto.response.StatementResponseDto;
import com.shivam.CreditMate.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of {@link StatementController} for handling operations related to statements.
 */
@RestController
public class StatementControllerImpl implements StatementController {

    private final StatementService statementService;

    /**
     * Constructs an instance of {@link StatementControllerImpl}.
     *
     * @param statementService the service to manage statement-related operations
     */
    @Autowired
    public StatementControllerImpl(StatementService statementService) {
        this.statementService = statementService;
    }

    /**
     * Generates a new statement based on the provided request details.
     *
     * @param statementRequestDto the details for generating a new statement
     * @return a {@link ResponseEntity} containing the generated statement and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<StatementResponseDto> generateStatement(StatementRequestDto statementRequestDto) {
        StatementResponseDto responseDto = statementService.generateStatement(statementRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves a statement by its UUID.
     *
     * @param statementUuid the unique identifier of the statement
     * @return a {@link ResponseEntity} containing the statement details and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<StatementResponseDto> getStatementByUuid(String statementUuid) {
        StatementResponseDto statement = statementService.getStatementByUuid(statementUuid);
        return ResponseEntity.ok(statement);
    }

    /**
     * Retrieves the latest statement for a specific card number.
     *
     * @param cardNumber the card number to fetch the latest statement for
     * @return a {@link ResponseEntity} containing the latest statement and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<StatementResponseDto> getLatestStatement(String cardNumber) {
        StatementResponseDto latestStatement = statementService.getLatestStatement(cardNumber);
        return ResponseEntity.ok(latestStatement);
    }
}
