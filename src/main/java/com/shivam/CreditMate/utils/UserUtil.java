package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.exception.exceptions.AuthException;
import com.shivam.CreditMate.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
    /**
     * Converts a given string to a corresponding Role enum.
     *
     * @param roleString the role in string format (case insensitive)
     * @return the Role enum corresponding to the given string
     * @throws IllegalArgumentException if the provided string does not match any Role
     */
    public static Role convertStringToRole(String roleString) {
        try {
            return Role.valueOf(roleString.toUpperCase()); // Ensure case-insensitivity
        } catch (IllegalArgumentException | NullPointerException e) {
            // Handle invalid role string or null value
            throw new IllegalArgumentException("Invalid role: " + roleString);
        }
    }

    public static User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (User) principal;
        } else {
            throw new AuthException.InvalidCredentialsException();
        }
    }
}
