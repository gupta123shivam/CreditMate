package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionRequestDto {
    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Transaction type is required")
    private String type; // CREDIT or DEBIT

    private String description;
}
