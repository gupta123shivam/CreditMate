package com.shivam.CreditMate.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class StatementResponseDto {

    private String statementUuid;
    private String cardNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TransactionResponseDto> transactions;
    private Double totalCredit;
    private Double totalDebit;
    private Double closingBalance;
}
