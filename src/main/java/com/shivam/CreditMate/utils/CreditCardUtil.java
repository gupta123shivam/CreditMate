package com.shivam.CreditMate.utils;

import java.util.Random;

public class CreditCardUtil {

    // TODO
    // later on can be fetched from an API
    public static Double generateCreditLimit() {
        // Generates a random double between 10.0 (inclusive) and 100.0 (inclusive)
        return 10.0 + (100.1) * new Random().nextDouble();
    }

    // Method to generate a credit card number with a given prefix
    public static String generateCreditCardNumber() {
        StringBuilder cardNumber = new StringBuilder(4);

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
}
