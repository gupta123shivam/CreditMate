package com.shivam.CreditMate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String id;
    private String fullname;
    private String email;
    private String role;
    private String username;
    private String uuid;
}
