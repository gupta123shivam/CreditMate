package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.response.StatementResponseDto;

import java.time.LocalDate;

public interface StatementService {

    StatementResponseDto generateStatement(String cardNumber, LocalDate startDate, LocalDate endDate);

    StatementResponseDto getStatementByUuid(String statementUuid);

    StatementResponseDto getLatestStatement(String cardNumber);
}
