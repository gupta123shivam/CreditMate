package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.StatementRequestDto;
import com.shivam.CreditMate.dto.response.StatementResponseDto;

import java.time.LocalDate;

public interface StatementService {

    StatementResponseDto generateStatement(StatementRequestDto statementRequestDto);

    StatementResponseDto getStatementByUuid(String statementUuid);

    StatementResponseDto getLatestStatement(String cardNumber);
}
