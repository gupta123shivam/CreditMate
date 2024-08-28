package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.UserUpdateRequestDto;
import com.shivam.CreditMate.exception.exceptions.AuthException.UserNotFoundException;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User findByUuid(String uuid) {
        // TODO change from id to uuid in production
        return userRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public User updateUserProfile(UserUpdateRequestDto userUpdateRequestDto) {
        User currentUser = UserUtil.getLoggedInUser();
        currentUser.setFullname(userUpdateRequestDto.getFullname());
        currentUser.setPassword(passwordEncoder.encode(userUpdateRequestDto.getPassword()));
        return userRepository.save(currentUser);
    }
}
