package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
