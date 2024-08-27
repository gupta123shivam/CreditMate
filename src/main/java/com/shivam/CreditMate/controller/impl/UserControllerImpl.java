package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.UserController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.exception.exceptions.AuthException.*;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;

    // TODO for admin only
    // Admin only access
    @PreAuthorize("hasRole('OWNER')")
    @Override
    @GetMapping("/{_uuid}")
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid) {
        // TODO change to uuid in production
        // Retrieve user details using the UserService
//        User user = userService.findByUuid(uuid);
        User user = userRepository.findById(Long.parseLong(uuid)).orElseThrow(UserNotFoundException::new);
        UserDetailsDto userDetailsDto = userMapper.userToUserDetailsDto(user);
        return ResponseEntity.ok(userDetailsDto);
    }


    // Authenticated user only access
    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @Override
    @GetMapping("/me")
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

    @GetMapping("/test")
    void test(Principal principal) {
        System.out.println(principal);
    }
}
