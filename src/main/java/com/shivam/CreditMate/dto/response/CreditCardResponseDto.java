package com.shivam.CreditMate.dto.response;

import com.shivam.CreditMate.dto.UserDetailsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreditCardResponseDto {
    private String cardNumber;
    private String uuid;
    private UserDetailsDto user;
    private String userUuid;
    private Double creditLimit;
    private Double currentBalance;
    private String status; // Can be ACTIVE, BLOCKED, etc.
    private String transactionRight;
    private boolean activated;
    private String createdAt;
}
