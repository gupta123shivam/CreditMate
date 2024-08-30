package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.exception.exceptions.AuthException.EmailAlreadyExistsException;
import com.shivam.CreditMate.exception.exceptions.AuthException.InvalidCredentialsException;
import com.shivam.CreditMate.exception.exceptions.AuthException.UserNotFoundException;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.security.JwtUtil;
import com.shivam.CreditMate.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto input) {
        log.info("Entering registerUser from authService: {}", input);

        // Check if the email already exists
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Create and save new user
        User user = User.builder()
                .fullname(input.getFullname())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(Role.ROLE_OWNER)
                .build();
        User savedUser = userRepository.save(user);

        // Return response DTO
        return userMapper.userToRegisterResponseDto(savedUser);
    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto input) {
        input.setUsername(input.getEmail());
        try {
            // Find the user by email
            User foundUser = userRepository.findByUsername(input.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("User not found."));

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );

            // Mark user as logged in and save
            foundUser.setLoggedIn(true);
            userRepository.save(foundUser);

            // Generate JWT token
            User userDetails = (User) authentication.getPrincipal();
            String jwtToken = jwtUtil.generateToken(userDetails);

            // Prepare and return login response
            LoginResponseDto loginResponseDto = userMapper.userToLoginResponseDto(userDetails);
            loginResponseDto.setJwtToken(jwtToken);

            return loginResponseDto;
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }

    @Override
    public String logoutUser(User user) {
        try {
            // Mark user as logged out and save
            user.setLoggedIn(false);
            userRepository.save(user);

            return "User logged out successfully";
        } catch (Exception e) {
            return "Logout failed";
        }
    }
}
