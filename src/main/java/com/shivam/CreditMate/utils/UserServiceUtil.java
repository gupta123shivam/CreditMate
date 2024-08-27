package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserServiceUtil {
    /**
     * Converts a given string to a corresponding Role enum.
     *
     * @param roleString the role in string format (case insensitive)
     * @return the Role enum corresponding to the given string
     * @throws IllegalArgumentException if the provided string does not match any Role
     */
    public Role convertStringToRole(String roleString) {
        try {
            return Role.valueOf(roleString.toUpperCase()); // Ensure case-insensitivity
        } catch (IllegalArgumentException | NullPointerException e) {
            // Handle invalid role string or null value
            throw new IllegalArgumentException("Invalid role: " + roleString);
        }
    }
}
