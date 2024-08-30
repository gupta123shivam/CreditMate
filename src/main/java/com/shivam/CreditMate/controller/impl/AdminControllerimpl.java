package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.AdminController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of the {@link AdminController} interface.
 * Handles administrative operations such as retrieving user details.
 */
@RestController
public class AdminControllerimpl implements AdminController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructs an instance of {@link AdminControllerimpl}.
     *
     * @param userRepository the repository for accessing user data
     * @param userMapper     the mapper for converting between User and UserDetailsDto
     */
    @Autowired
    public AdminControllerimpl(UserRepository userRepository, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    /**
     * Test endpoint for administrative access.
     * This endpoint is meant to be accessible only by users with ROLE_ADMIN role.
     *
     * @return a string indicating access restriction
     */
    @Override
    public String testAdmin() {
        return "This must be only accessible to ROLE_ADMIN";
    }

    /**
     * Retrieves user details by UUID.
     * Accessible only by users with ROLE_ADMIN role.
     *
     * @param uuid the UUID of the user to retrieve
     * @return a ResponseEntity containing the user details
     */
    @Override
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid) {
        // TODO: convert UUID to Long for the database query or adjust repository method as needed
        // TODO: replace UUID parsing with appropriate logic when UUID is used in production

        // Retrieve user details from the repository
        User user = userRepository.findById(Long.parseLong(uuid))
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_2002));

        // Map the User entity to UserDetailsDto
        UserDetailsDto userDetailsDto = userMapper.userToUserDetailsDto(user);

        // Return user details as a ResponseEntity
        return ResponseEntity.ok(userDetailsDto);
    }
}
