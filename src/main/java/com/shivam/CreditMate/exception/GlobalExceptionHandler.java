package com.shivam.CreditMate.exception;

import com.shivam.CreditMate.exception.exceptions.AuthException.*;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return buildErrorResponse(AppErrorCodes.ERR_2001, ex.getMessage());

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(AppErrorCodes.ERR_2002, ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return buildErrorResponse(AppErrorCodes.ERR_2003, ex.getMessage());
    }

    @ExceptionHandler(CreditCardDoesNotExist.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(CreditCardDoesNotExist ex) {
        return buildErrorResponse(AppErrorCodes.ERR_3001, ex.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedForThisCreditCard.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(UserNotAuthorizedForThisCreditCard ex) {
        return buildErrorResponse(AppErrorCodes.ERR_3002, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String field = ((FieldError) error).getField();
                    String defaultMessage = error.getDefaultMessage();
                    return String.format("Field '%s': %s", field, defaultMessage);
                })
                .collect(Collectors.toList());

        return buildErrorResponse(AppErrorCodes.ERR_1001, errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(error -> {
                    String field = ((FieldError) error).getField();
                    String defaultMessage = ((FieldError) error).getDefaultMessage();
                    return String.format("Field '%s': %s", field, defaultMessage);
                })
                .collect(Collectors.toList());

        return buildErrorResponse(AppErrorCodes.ERR_1005, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return buildErrorResponse(AppErrorCodes.ERR_4001, ex.getMessage());
    }


    // Helper method to build error response
    private ResponseEntity<ErrorResponse> buildErrorResponse(AppErrorCodes errorCode, String errorDetail) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .details(List.of(errorDetail))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(AppErrorCodes errorCode, List<String> errors) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .details(errors)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }
}
