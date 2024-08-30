package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileUpdateRequestDto {
    @NotBlank(message = "Full name is mandatory")
    private String fullname;

    @NotBlank(message = "Password is necessary")
    private String password;
}
