package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.exception.exceptions.AuthException;
import com.shivam.CreditMate.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserUtil {

    /**
     * Converts a given string to a corresponding Role enum.
     *
     * @param roleString the role in string format
     * @return the Role enum corresponding to the given string
     */
    public static Role convertStringToRole(String roleString) {
        try {
            return Role.valueOf(roleString.toUpperCase()); // Convert to uppercase for case-insensitivity
        } catch (Exception e) {
            // Handle invalid role string or null value
            throw new RuntimeException("Invalid role: " + roleString);
        }
    }

    /**
     * Retrieves the currently logged-in user from the security context.
     *
     * @return the currently logged-in User
     */
    public static User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            // Throw exception if authentication is missing or principal is not a User
            throw new AuthException.InvalidCredentialsException();
        }
        return (User) authentication.getPrincipal();
    }
}
