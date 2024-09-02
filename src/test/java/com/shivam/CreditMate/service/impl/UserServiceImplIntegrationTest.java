package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.PasswordChangeDto;
import com.shivam.CreditMate.dto.request.UserProfileUpdateRequestDto;
import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.security.JwtUtil;
import com.shivam.CreditMate.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceImplIntegrationTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    private User testUser;

    private String testUserPassword;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();

        testUserPassword = "password";
        testUser = User.builder()
                .email("test@example.com")
                .username("testuser")
                .fullname("Test User")
                .role(Role.ROLE_OWNER)
                .password(passwordEncoder.encode(testUserPassword))
                .build();

        userRepository.save(testUser);
    }

    @Test
    public void testFindByUuid_Success() {
        User foundUser = userService.findByUuid(testUser.getUuid());

        assertNotNull(foundUser);
        assertEquals(testUser.getUuid(), foundUser.getUuid());
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testFindByUuid_NotFound() {
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.findByUuid("non-existent-uuid");
        });

        assertEquals("ERR_2002", exception.getError().getCode());
        assertEquals("User not found", exception.getError().getMessage());
        assertEquals(404, exception.getError().getHttpStatus().value());
    }

    @Test
    public void testLoadUserByEmail_Success() {
        User foundUser = userService.loadUserByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testLoadUserByEmail_NotFound() {
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.loadUserByEmail("notfound@example.com");
        });

        assertEquals("ERR_2002", exception.getError().getCode());
        assertEquals("User not found", exception.getError().getMessage());
        assertEquals(404, exception.getError().getHttpStatus().value());
    }

    @Test
    public void testLoadUserByUsername_Success() {
        String username = testUser.getUsername();
        User foundUser = userService.loadUserByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.loadUserByUsername("nonexistentuser");
        });

        assertEquals("ERR_2002", exception.getError().getCode());
        assertEquals("User not found", exception.getError().getMessage());
        assertEquals(404, exception.getError().getHttpStatus().value());
    }
// TODO needs jwt

    @Test
    public void testUpdateUserProfile_Success() {
        UserProfileUpdateRequestDto updateRequest = new UserProfileUpdateRequestDto();
        updateRequest.setFullname("Updated Name");
        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            User updatedUser = userService.updateUserProfile(updateRequest);

            assertNotNull(updatedUser);
            assertEquals("Updated Name", updatedUser.getFullname());

            User foundUser = userRepository.findByUuid(testUser.getUuid()).orElse(null);
            assertNotNull(foundUser);
            assertEquals("Updated Name", foundUser.getFullname());
        }
    }

    //
    @Test
    public void testUpdatePassword_Success() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword(testUserPassword);
        passwordChangeDto.setNewPassword("newPassword");

        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            userService.updatePassword(passwordChangeDto);

            User foundUser = userRepository.findByUuid(testUser.getUuid()).orElse(null);
            assertNotNull(foundUser);
            assertTrue(passwordEncoder.matches(passwordChangeDto.getNewPassword(), foundUser.getPassword()));
        }
    }

    @Test
    public void testUpdatePasswordFailure_WrongOldPassword() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword("wrongOldPassword");
        passwordChangeDto.setNewPassword("newPassword");
        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            CustomException exception = assertThrows(CustomException.class, () -> {
                userService.updatePassword(passwordChangeDto);
            });

            assertEquals("ERR_5002", exception.getError().getCode());
            assertEquals("old password is not matching", exception.getError().getMessage());
            assertEquals(400, exception.getError().getHttpStatus().value());
        }

    }
}