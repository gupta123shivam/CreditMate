package com.shivam.CreditMate.exception.exceptions;

public class UserException {
    public static class InvalidUserUpdateException extends RuntimeException {
        public InvalidUserUpdateException() {
        }

        public InvalidUserUpdateException(String message) {
            super(message);
        }
    }
}
