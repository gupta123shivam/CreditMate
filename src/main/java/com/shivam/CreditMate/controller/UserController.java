package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.UserDto;
import com.shivam.CreditMate.dto.LoginDto;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        // Implement JWT authentication and return JWT token
        return "JWT Token";
    }

//    @GetMapping("/{_uuid}")
//    public User getUserByUuid(@PathVariable String uuid) {
//
//        return userService.findByUuid();
//    }
}
