package com.shivam.CreditMate.exception.exceptions;

import com.shivam.CreditMate.exception.AppErrorCodes;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    final AppErrorCodes error;
    final Exception caughtException;

    public CustomException(AppErrorCodes error, Exception caughtException) {
        this.error = error;
        this.caughtException = caughtException;
    }

    public CustomException(AppErrorCodes error) {
        this.error = error;
        this.caughtException = new Exception();
    }
}
