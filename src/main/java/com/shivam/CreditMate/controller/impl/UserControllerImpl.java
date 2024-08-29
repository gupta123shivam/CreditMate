package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.UserController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.dto.request.UserUpdateRequestDto;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of {@link UserController} for managing user-related operations.
 */
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves the profile of the currently logged-in user.
     *
     * @return a {@link ResponseEntity} containing the user profile details and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<UserDetailsDto> getUserProfile() {
        // Fetch the currently logged-in user
        User currentUser = UserUtil.getLoggedInUser();

        // Convert the User entity to UserDetailsDto
        UserDetailsDto userDetails = userMapper.userToUserDetailsDto(currentUser);

        return ResponseEntity.ok(userDetails);
    }

    /**
     * Updates the profile of the currently logged-in user based on the provided request details.
     *
     * @param userUpdateRequestDto the request containing updated user profile details
     * @return a {@link ResponseEntity} containing the updated user profile details and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<UserDetailsDto> updateUserProfile(UserUpdateRequestDto userUpdateRequestDto) {
        // Update the user profile using the service
        User updatedUser = userService.updateUserProfile(userUpdateRequestDto);

        // Convert the updated User entity to UserDetailsDto
        UserDetailsDto userDetailsDto = userMapper.userToUserDetailsDto(updatedUser);

        return ResponseEntity.ok(userDetailsDto);
    }
}
