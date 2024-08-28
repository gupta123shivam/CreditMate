package com.shivam.CreditMate.exception.exceptions;

public class CreditCardException {
    public static class CreditCardDoesNotExist extends RuntimeException {
        public CreditCardDoesNotExist() {
        }

        public CreditCardDoesNotExist(String msg) {
            super(msg);
        }
    }

    public static class UserNotAuthorizedForThisCreditCard extends RuntimeException {
        public UserNotAuthorizedForThisCreditCard() {
        }

        public UserNotAuthorizedForThisCreditCard(String message) {
            super(message);
        }
    }
}
