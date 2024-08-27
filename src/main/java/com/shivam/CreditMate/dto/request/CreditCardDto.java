package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditCardDto {

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotBlank(message = "User UUID is required")
    private String userUuid;

    @NotNull(message = "Credit limit is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Credit limit must be greater than 0")
    private Double creditLimit;

    @NotNull(message = "Current balance is required")
    @DecimalMin(value = "0.0", message = "Current balance cannot be negative")
    @DecimalMax(value = "1000000.0", message = "Current balance cannot exceed 1,000,000")
    private Double currentBalance;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|BLOCKED|SUSPENDED|CLOSED", message = "Status must be one of the following: ACTIVE, BLOCKED, SUSPENDED, CLOSED")
    private String status; // Can be ACTIVE, BLOCKED, etc.

    @NotBlank(message = "Transaction right is required")
    @Pattern(regexp = "FULL_ACCESS|VIEW_ONLY|NO_ACCESS", message = "Transaction right must be one of the following: FULL_ACCESS, VIEW_ONLY, NO_ACCESS")
    private String transactionRight;

    private boolean activated;
}
