package com.shivam.CreditMate.exception;

import org.springframework.http.HttpStatus;

public enum AppErrorCodes implements ErrorCode {

    // Validation errors
    ERR_1001("Field cannot be empty", HttpStatus.BAD_REQUEST),
    ERR_1002("Field length must be between 2 and 10 characters", HttpStatus.BAD_REQUEST),
    ERR_1003("Invalid email format", HttpStatus.BAD_REQUEST),
    ERR_1004("Password must contain at least one uppercase letter, one lowercase letter, and one number", HttpStatus.BAD_REQUEST),
    ERR_1005("Constraints not followed", HttpStatus.BAD_REQUEST),

    // Auth errors
    ERR_2001("Email already exists", HttpStatus.BAD_REQUEST),
    ERR_2002("User not found", HttpStatus.NOT_FOUND),
    ERR_2003("Unauthorized access", HttpStatus.UNAUTHORIZED),
    ERR_2004("Wrong credentials", HttpStatus.UNAUTHORIZED),

    //Credit Card Exception
    ERR_3001("Credit Card does not exist.", HttpStatus.NOT_FOUND),
    ERR_3002("User is not authorized to perform action on this credit card.", HttpStatus.UNAUTHORIZED),
    ERR_3003("Credit card has negative balance. Please pay ur dues.", HttpStatus.CONFLICT),

    // Exceptions
    ERR_4001("Something bad happened. please try later", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_4002("Something bad happened at Runtime. please try later", HttpStatus.INTERNAL_SERVER_ERROR),

    // User error
    ERR_5001("Invalid User profile update values", HttpStatus.BAD_REQUEST),
    ERR_5002("old password is not matching", HttpStatus.BAD_REQUEST),

    // Transaction error
    ERR_6001("Insufficient Balance", HttpStatus.BAD_REQUEST),
    ERR_6002("Transaction with given Id not found", HttpStatus.BAD_REQUEST),

    // Statement Error
    ERR_7001("Statement with provided Uuid was not found", HttpStatus.NOT_FOUND),
    ERR_7002("User is not authorized to perform action on this statement.", HttpStatus.UNAUTHORIZED),
    ERR_7003("No statement exist yet. Please generate statement first.", HttpStatus.BAD_REQUEST),
    ERR_7004("Could not serialize transactions list to JSON", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_7005("Could not deserialize transactions JSON to list", HttpStatus.INTERNAL_SERVER_ERROR),

    // Encryption Error
    ERR_8001("Error encrypting card number", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_8002("Error decrypting card number", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_8003("Something bad happened while encryption", HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_8004("Something bad happened while decryption", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String message;
    private final HttpStatus httpStatus;

    AppErrorCodes(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
