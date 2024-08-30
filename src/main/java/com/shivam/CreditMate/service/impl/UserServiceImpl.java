package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.UserUpdateRequestDto;
import com.shivam.CreditMate.exception.exceptions.AuthException.UserNotFoundException;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final
    UserRepository userRepository;

    private final
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUuid(String uuid) {
        // Fetch user by UUID, throw exception if not found
        return userRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public User loadUserByEmail(String email) {
        // Fetch user by email, throw exception if not found
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public User loadUserByUsername(String username) {
        // Fetch user by username, throw exception if not found
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public User updateUserProfile(UserUpdateRequestDto userUpdateRequestDto) {
        // Retrieve currently logged-in user
        User currentUser = UserUtil.getLoggedInUser();

        // Update user details and save changes
        currentUser.setFullname(userUpdateRequestDto.getFullname());
        currentUser.setPassword(passwordEncoder.encode(userUpdateRequestDto.getPassword()));

        return userRepository.save(currentUser);
    }
}
