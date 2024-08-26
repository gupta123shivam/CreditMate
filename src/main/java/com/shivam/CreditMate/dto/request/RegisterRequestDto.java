package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "Full name is mandatory")
    private String fullname;

    // TODO save min and max size as constants and insert it here
    @NotBlank(message = "Password is necessary")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
}
