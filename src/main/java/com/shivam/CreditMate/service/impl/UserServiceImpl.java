package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.PasswordChangeDto;
import com.shivam.CreditMate.dto.request.UserProfileUpdateRequestDto;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
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
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_2002));
    }

    @Override
    public User loadUserByEmail(String email) {
        // Fetch user by email, throw exception if not found
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_2002));
    }

    @Override
    public User loadUserByUsername(String username) {
        // Fetch user by username, throw exception if not found
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_2002));
    }

    @Override
    public User updateUserProfile(UserProfileUpdateRequestDto userProfileUpdateRequestDto) {
        // Retrieve currently logged-in user
        User currentUser = UserUtil.getLoggedInUser();

        // Update user details and save changes
        currentUser.setFullname(userProfileUpdateRequestDto.getFullname());

        return userRepository.save(currentUser);
    }

    @Override
    public void updatePassword(PasswordChangeDto input) {
        // Retrieve currently logged-in user
        User currentUser = UserUtil.getLoggedInUser();

        if (!passwordEncoder.matches(input.getOldPassword(), currentUser.getPassword()))
            throw new CustomException(AppErrorCodes.ERR_5002);

        currentUser.setPassword(passwordEncoder.encode(input.getNewPassword()));
        userRepository.save(currentUser);
    }
}
