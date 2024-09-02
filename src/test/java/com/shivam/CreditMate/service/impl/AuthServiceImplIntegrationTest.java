package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.security.JwtUtil;
import com.shivam.CreditMate.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceImplIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        // Clean up the user repository before each test
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("John Doe", "password123", "john.doe@example.com");

        // Act
        RegisterResponseDto response = authService.registerUser(registerRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals("John Doe", response.getFullname());
        assertEquals("john.doe@example.com", response.getEmail());
        assertTrue(userRepository.existsByEmail("john.doe@example.com"));

        User registeredUser = userRepository.findByEmail("john.doe@example.com").orElse(null);
        assertNotNull(registeredUser);
        assertTrue(passwordEncoder.matches("password123", registeredUser.getPassword()));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Arrange
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("John Doe", "password123", "john.doe@example.com");
        authService.registerUser(registerRequestDto); // Initial registration

        // Act & Assert
        RegisterRequestDto duplicateRequest = new RegisterRequestDto("Jane Doe", "password123", "john.doe@example.com");
        CustomException exception = assertThrows(CustomException.class, () -> authService.registerUser(duplicateRequest));
        assertEquals(AppErrorCodes.ERR_2001, exception.getError());
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Jane Doe", "password123", "jane.doe@example.com");
        RegisterResponseDto registerResponseDto = authService.registerUser(registerRequestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto("jane.doe@example.com", "password123", "jane.doe@example.com");

        // Act
        LoginResponseDto response = authService.loginUser(loginRequestDto);

        // Assert
        assertNotNull(response);
        assertEquals("Jane Doe", response.getFullname());
        assertEquals("jane.doe@example.com", response.getEmail());
        assertNotNull(response.getJwtToken());
//        assertTrue(jwtUtil.validateTokenWithCurrentUser(response.getJwtToken(), UserUtil.getLoggedInUser()));
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        // Arrange
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Jane Doe", "password123", "jane.doe@example.com");
        authService.registerUser(registerRequestDto);

        LoginRequestDto loginRequestDto = new LoginRequestDto("jane.doe@example.com", "wrongpassword", "jane.doe@example.com");

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> authService.loginUser(loginRequestDto));
        assertEquals(AppErrorCodes.ERR_2003, exception.getError());
    }

    @Test
    void testLoginUser_UserNotFound() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto("unknown@example.com", "password123", "unknown@example.com");

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> authService.loginUser(loginRequestDto));
        assertEquals(AppErrorCodes.ERR_2002, exception.getError());
    }

    @Test
    void testLogoutUser_Success() {
        // Arrange
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Jane Doe", "password123", "jane.doe@example.com");
        authService.registerUser(registerRequestDto);

        User user = userRepository.findByEmail("jane.doe@example.com").orElseThrow();

        // Act
        String result = authService.logoutUser(user);

        // Assert
        assertEquals("User logged out successfully", result);
        assertFalse(user.isLoggedIn());
    }

    @Test
    void testLogoutUser_Failure() {
        // Arrange
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("Jane Doe", "password123", "jane.doe@example.com");
        authService.registerUser(registerRequestDto);

        User user = userRepository.findByEmail("jane.doe@example.com").orElseThrow();
        userRepository.delete(user); // Simulate a failure by deleting the user before logout

        // Act
        String result = authService.logoutUser(user);

        // Assert
        assertEquals("Logout failed", result);
    }
}
