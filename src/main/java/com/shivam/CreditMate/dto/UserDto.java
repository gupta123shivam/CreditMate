package com.shivam.CreditMate.dto;

import com.shivam.CreditMate.constants.UserConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is necessary")
    @Size(min = UserConstants.PASSWORD_MIN_SIZE, max = UserConstants.PASSWORD_MAX_SIZE)
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Role is mandatory")
    private String role;
}
