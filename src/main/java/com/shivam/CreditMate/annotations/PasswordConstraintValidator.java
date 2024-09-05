package com.shivam.CreditMate.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Any initialization logic goes here
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;  // Handle null value case
        }
        return password.matches(PASSWORD_PATTERN);
    }
}
