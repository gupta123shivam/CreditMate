package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class StatementRequestDto {
    @NotBlank
    private String cardNumber;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
