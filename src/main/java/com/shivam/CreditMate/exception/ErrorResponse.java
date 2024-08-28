package com.shivam.CreditMate.exception;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String errorCode;
    private String message;
    private List<String> details;
    private LocalDateTime timestamp;

    // Constructors
    public ErrorResponse(int status, ErrorCode errorCode, List<String> details) {
        this.status = status;
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.details = details;
    }

    public ErrorResponse(int status, ErrorCode errorCode) {
        this(status, errorCode, null);
    }
}
