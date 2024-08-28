package com.shivam.CreditMate.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class UserDetailsDto {
    private String uuid;
    private String fullname;
    private String email;
    private String username;
    private String role;
    private LocalDateTime createdAt;
}
