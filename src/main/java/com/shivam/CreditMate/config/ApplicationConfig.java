package com.shivam.CreditMate.config;

import com.shivam.CreditMate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for application security and authentication management.
 * This class defines beans related to security configuration, such as password encoding,
 * authentication provider, and authentication manager.
 */
@Configuration
public class ApplicationConfig {

    private final UserService userService;

    /**
     * Constructor for ApplicationConfig.
     *
     * @param userService the user service used for loading user details
     */
    @Autowired
    public ApplicationConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Bean definition for PasswordEncoder using BCrypt.
     * This bean is responsible for encoding passwords with BCrypt hashing algorithm.
     *
     * @return a PasswordEncoder instance of BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for UserDetailsService.
     * This bean provides a method to load user-specific data provided by userService.
     *
     * @return a User object which implements UserDetails
     */
    @Bean
    UserDetailsService userDetailsService() {
        return userService::loadUserByUsername;
    }

    /**
     * Bean definition for AuthenticationManager.
     * This bean manages authentication processes in the application.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean definition for AuthenticationProvider.
     * This bean provides an implementation of DaoAuthenticationProvider,
     * which uses a UserDetailsService and PasswordEncoder for authentication.
     *
     * @return an AuthenticationProvider instance configured with userDetailsService and passwordEncoder
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
