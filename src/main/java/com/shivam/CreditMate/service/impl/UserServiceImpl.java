package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.UserDto;
import com.shivam.CreditMate.exception.userService.EmailAlreadyExistsException;
import com.shivam.CreditMate.exception.userService.UserAlreadyExistsException;
import com.shivam.CreditMate.exception.userService.UsernameNotFoundException;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import com.shivam.CreditMate.utils.UserServiceUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    
    @Override
    public User registerUser(@Valid UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User
                .builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(UserServiceUtil.passwordEncoder(userDto.getPassword()))
                .role(UserServiceUtil.convertStringToRole(userDto.getRole()))
                .uuid(UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public User findByUuid(UUID uuid) {
        Optional<User> optionalUser = userRepository.findByUuid(uuid);
        return optionalUser.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
    }
}
