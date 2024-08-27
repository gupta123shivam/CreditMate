package com.shivam.CreditMate.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String code;
    private String message;
    private List<String> errors;

    // Constructors
    public ErrorResponse(int status, ErrorCode errorCode, List<String> errors) {
        this.status = status;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }

    public ErrorResponse(int status, ErrorCode errorCode) {
        this(status, errorCode, null);
    }
}
