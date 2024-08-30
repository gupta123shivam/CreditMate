package com.shivam.CreditMate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto {
    @NotBlank
    private String oldPassword;
    @NotBlank(message = "Password can NOT be empty")
    private String newPassword;
}
