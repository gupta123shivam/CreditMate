package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.exception.userService.EmailAlreadyExistsException;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.shivam.CreditMate.enums.Role;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;
    
    @Autowired private UserMapper userMapper;

    @Override
    public ResponseEntity<RegisterResponseDto> registerUser(RegisterRequestDto input) {
        // Check if this email already exists or not
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Create a new User object
        User user = User.builder()
                .fullname(input.getFullname())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(Role.OWNER)
                .build();

        // Save the user
        User savedUser = userRepository.save(user);

        // Map saved user to RegisterResponseDto
        RegisterResponseDto body = userMapper.userToRegisterResponseDto(savedUser);

        // Return response entity with CREATED status
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/users/" + savedUser.getUuid()));
        
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(body);
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return null;
    }
}
