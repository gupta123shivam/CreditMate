package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.CreditCardDoesNotExist;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.UserNotAuthorizedForThisCreditCard;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.CreditCardRepository;

import java.util.Random;

public final class CreditCardUtil {
    // TODO
    // later on can be fetched from an API

    /**
     * Generate credit limit for the card
     */
    public static Double generateCreditLimit() {
        // Generates a random double between 10.0 (inclusive) and 100.0 (inclusive)
        double limit = 10.0 + (100.1) * new Random().nextDouble();
        return Math.round(limit * 100) * 1.0 / 100; // make limit up to 2 decimal places
    }

    /**
     * Method to generate a credit card number
     */
    public static String generateCreditCardNumber() {
        StringBuilder cardNumber = new StringBuilder(16);
        cardNumber.append(4);

        // Generate random digits to fill the card number to 15 digits (excluding the last check digit)
        Random random = new Random();
        while (cardNumber.length() < 15) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }

        // Calculate and append the Luhn check digit
        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    // Method to calculate the Luhn check digit
    public static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;

        // Process digits from right to left
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }

            sum += n;
            alternate = !alternate;
        }

        return (sum * 9) % 10;
    }

    /**
     * checking is current user is authorized to perform action or not
     */
    public static CreditCard findByIdAndCurrentUser(CreditCardRepository creditCardRepository, Long cardId) {
        CreditCard creditCard = creditCardRepository.findById(cardId)
                .orElseThrow(CreditCardDoesNotExist::new);
        if (!creditCard.getUserUuid().equals(UserUtil.getLoggedInUser().getUuid()))
            throw new UserNotAuthorizedForThisCreditCard();
        else return creditCard;
    }

    /**
     * check if cardNumber belongs to current authenticated user and return the card
     */
    public static CreditCard getCreditCardByCardNumber(CreditCardRepository creditCardRepository, String cardNumber) {
        User currentUser = UserUtil.getLoggedInUser();

        // make sure that card attached to transaction is still in system
        CreditCard creditCard = creditCardRepository
                .findByCardNumber(cardNumber)
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_3001));

        // make sure current user is owner of this card
        if (!currentUser.getUuid().equals(creditCard.getUserUuid()))
            throw new CustomException(AppErrorCodes.ERR_3002);
        return creditCard;
    }
}
