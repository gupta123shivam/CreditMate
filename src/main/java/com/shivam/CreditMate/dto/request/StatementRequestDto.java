package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatementRequestDto {
    @NotBlank
    private String cardNumber;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;
}
