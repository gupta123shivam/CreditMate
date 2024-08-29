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

    /**
     * Generate a random credit limit between 10.0 and 100.0, rounded to 2 decimal places.
     *
     * @return the generated credit limit.
     */
    public static Double generateCreditLimit() {
        double limit = 10.0 + (100.0 * new Random().nextDouble());
        return Math.round(limit * 100) / 100.0;
    }

    /**
     * Generate a valid credit card number using the Luhn algorithm.
     *
     * @return the generated credit card number.
     */
    public static String generateCreditCardNumber() {
        StringBuilder cardNumber = new StringBuilder(16);
        cardNumber.append(4);  // Starting digit for Visa cards

        Random random = new Random();
        while (cardNumber.length() < 15) {
            cardNumber.append(random.nextInt(10));
        }

        // Append the Luhn check digit
        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    /**
     * Calculate the Luhn check digit for a given number.
     *
     * @param number the number to calculate the check digit for.
     * @return the Luhn check digit.
     */
    public static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;

        // Process digits from right to left
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum * 9) % 10;
    }

    /**
     * Find a credit card by ID and ensure the current user is authorized to access it.
     *
     * @param creditCardRepository the repository to query.
     * @param cardId               the ID of the credit card.
     * @return the credit card if found and authorized.
     */
    public static CreditCard findByIdAndCurrentUser(CreditCardRepository creditCardRepository, Long cardId) {
        CreditCard creditCard = creditCardRepository.findById(cardId)
                .orElseThrow(CreditCardDoesNotExist::new);
        if (!creditCard.getUserUuid().equals(UserUtil.getLoggedInUser().getUuid())) {
            throw new UserNotAuthorizedForThisCreditCard();
        }
        return creditCard;
    }

    /**
     * Get a credit card by its number and verify that it belongs to the current user.
     *
     * @param creditCardRepository the repository to query.
     * @param cardNumber           the credit card number.
     * @return the credit card if found and belongs to the user.
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
