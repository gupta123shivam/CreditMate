package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.PasswordChangeDto;
import com.shivam.CreditMate.dto.request.UserProfileUpdateRequestDto;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.utils.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        // Initialize a test user for use in multiple test cases
        testUser = User.builder()
                .email("test@example.com")
                .username("testuser")
                .fullname("Test User")
                .password("encodedPassword").build();
        testUser.setPassword(passwordEncoder.encode(testUser.getPassword()));
    }

    @Test
    public void testFindByUuidSuccess() {
        // Mock the repository to return the test user
        when(userRepository.findByUuid(testUser.getUuid())).thenReturn(Optional.of(testUser));

        User foundUser = userService.findByUuid(testUser.getUuid());

        assertNotNull(foundUser);
        assertEquals(testUser.getUuid(), foundUser.getUuid());
        verify(userRepository, times(1)).findByUuid(testUser.getUuid());
    }

    @Test
    public void testFindByUuidNotFound() {
        // Mock the repository to return an empty result
        when(userRepository.findByUuid("non-existent-uuid")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.findByUuid("non-existent-uuid");
        });

        assertEquals(AppErrorCodes.ERR_2002, exception.getError());
    }

    @Test
    public void testLoadUserByEmailSuccess() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        User foundUser = userService.loadUserByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testLoadUserByEmailNotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.loadUserByEmail("notfound@example.com");
        });

        assertEquals(AppErrorCodes.ERR_2002, exception.getError());
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        User foundUser = userService.loadUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.loadUserByUsername("nonexistentuser");
        });

        assertEquals(AppErrorCodes.ERR_2002, exception.getError());
    }

    @Test
    public void testUpdateUserProfileSuccess() {
        UserProfileUpdateRequestDto updateRequest = new UserProfileUpdateRequestDto();
        updateRequest.setFullname("Updated Name");

        // Use try-with-resources to mock the currently logged-in user
        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            when(userRepository.save(any(User.class))).thenReturn(testUser);

            User updatedUser = userService.updateUserProfile(updateRequest);

            assertNotNull(updatedUser);
            assertEquals("Updated Name", updatedUser.getFullname());
            verify(userRepository, times(1)).save(testUser);
        }
    }

    @Test
    public void testUpdatePasswordSuccess() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword("oldPassword");
        passwordChangeDto.setNewPassword("newPassword");

        // Use try-with-resources to mock the currently logged-in user
        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            when(passwordEncoder.matches(passwordChangeDto.getOldPassword(), testUser.getPassword())).thenReturn(true);
            when(passwordEncoder.encode(passwordChangeDto.getNewPassword())).thenReturn("encodedNewPassword");

            userService.updatePassword(passwordChangeDto);

            verify(userRepository, times(1)).save(testUser);
            assertEquals("encodedNewPassword", testUser.getPassword());
        }
    }

    @Test
    public void testUpdatePasswordFailure_WrongOldPassword() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setOldPassword("wrongOldPassword");
        passwordChangeDto.setNewPassword("newPassword");

        // Use try-with-resources to mock the currently logged-in user
        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            when(passwordEncoder.matches(eq("wrongOldPassword"), any())).thenReturn(false);

            CustomException exception = assertThrows(CustomException.class, () -> {
                userService.updatePassword(passwordChangeDto);
            });

            assertEquals(AppErrorCodes.ERR_5002, exception.getError());
        }
    }

    @Test
    public void testUpdateUserProfileFailure_NoLoggedInUser() {
        UserProfileUpdateRequestDto updateRequest = new UserProfileUpdateRequestDto();
        updateRequest.setFullname("Updated Name");

        // Use try-with-resources to mock the absence of a logged-in user
        try (MockedStatic<UserUtil> mockedStatic = Mockito.mockStatic(UserUtil.class)) {
            mockedStatic.when(UserUtil::getLoggedInUser).thenThrow(new CustomException(AppErrorCodes.ERR_2002));

            CustomException exception = assertThrows(CustomException.class, () -> {
                userService.updateUserProfile(updateRequest);
            });

            assertEquals(AppErrorCodes.ERR_2002, exception.getError());
        }
    }
}
