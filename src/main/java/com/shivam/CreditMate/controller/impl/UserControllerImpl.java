package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.UserController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.exception.exceptions.AuthException.*;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<UserDetailsDto> getCurrentUser() {
        // Retrieve the current authenticated user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new InvalidCredentialsException();
        }

        // Fetch user details using the UserService
        User currentUser = (User) authentication.getPrincipal();
        UserDetailsDto userDetails = userMapper.userToUserDetailsDto(currentUser);

        return ResponseEntity.ok(userDetails);
    }
}
