package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.UserController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.dto.request.UserUpdateRequestDto;
import com.shivam.CreditMate.exception.exceptions.AuthException.*;
import com.shivam.CreditMate.exception.exceptions.UserException.*;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<UserDetailsDto> getUserProfile() {
        // Fetch user details using the UserService
        User currentUser = UserUtil.getLoggedInUser();
        UserDetailsDto userDetails = userMapper.userToUserDetailsDto(currentUser);

        return ResponseEntity.ok(userDetails);
    }

    @Override
    public ResponseEntity<UserDetailsDto> updateUserProfile(UserUpdateRequestDto userUpdateRequestDto) throws Exception {
        User updatedUser = userService.updateUserProfile(userUpdateRequestDto);
        UserDetailsDto userDetailsDto = userMapper.userToUserDetailsDto(updatedUser);
        return ResponseEntity.ok(userDetailsDto);
    }
}
