package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionRequestDto {
    @NotNull(message = "Card id is required")
    private Long cardId;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Amount is required")
    private Double amount;

    @Pattern(regexp = "DEBIT|CREDIT")
    @NotBlank(message = "Transaction type is required")
    private String transactionType; // CREDIT or DEBIT

    private String description;

    @Pattern(regexp = "PURCHASE|ENTERTAINMENT|PAYMENT|BILL|DEFAULT")
    private String transactionCategory;
}
