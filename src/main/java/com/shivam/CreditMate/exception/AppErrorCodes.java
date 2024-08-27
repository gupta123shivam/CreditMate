package com.shivam.CreditMate.exception;

public enum AppErrorCodes implements ErrorCode {

    // Validation errors
    ERR_1001("Field cannot be empty"),
    ERR_1002("Field length must be between {min} and {max} characters"),
    ERR_1003("Invalid email format"),
    ERR_1004("Password must contain at least one uppercase letter, one lowercase letter, and one number"),

    // Business logic errors
    ERR_2001("Email already exists"),
    ERR_2002("User not found"),
    ERR_2003("Invalid credentials");

    private final String message;

    AppErrorCodes(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
