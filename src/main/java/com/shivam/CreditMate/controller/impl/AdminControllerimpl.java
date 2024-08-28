package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.AdminController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.exception.exceptions.AuthException;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminControllerimpl implements AdminController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public AdminControllerimpl(UserRepository userRepository, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public String testAdmin() {
        return "This must be only accesaable to ADMIN";
    }

    @Override
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid) {
        // TODO change to uuid in production
        // Retrieve user details using the UserService
//        User user = userService.findByUuid(uuid);
        User user = userRepository.findById(Long.parseLong(uuid)).orElseThrow(AuthException.UserNotFoundException::new);
        UserDetailsDto userDetailsDto = userMapper.userToUserDetailsDto(user);
        return ResponseEntity.ok(userDetailsDto);
    }
}
