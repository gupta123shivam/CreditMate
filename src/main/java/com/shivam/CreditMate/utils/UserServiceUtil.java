package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.enums.Role;

public class UserServiceUtil {
    public static Role convertStringToRole(String roleString) {
        try {
            return Role.valueOf(roleString.toUpperCase()); // Ensure case-insensitivity
        } catch (IllegalArgumentException | NullPointerException e) {
            // Handle invalid role string or null value
            throw new IllegalArgumentException("Invalid role: " + roleString);
        }
    }
}
