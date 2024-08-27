package com.shivam.CreditMate.exception.exceptions;

public class AuthException {
    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException() {
        }

        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException() {
        }

        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
        }

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
