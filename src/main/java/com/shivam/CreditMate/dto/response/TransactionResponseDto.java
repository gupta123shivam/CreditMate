package com.shivam.CreditMate.dto.response;

import com.shivam.CreditMate.enums.TransactionCategory;
import com.shivam.CreditMate.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionResponseDto {
    private String transactionUuid;
    private String cardNumber;
    private Double amount;
    private TransactionType transactionType;
    private TransactionCategory transactionCategory;
    private String description;
    private LocalDateTime transactionDate;
}
