package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.dto.request.LoginRequestDto;
import com.shivam.CreditMate.dto.request.RegisterRequestDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.service.AuthService;
import com.shivam.CreditMate.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl {
    @Autowired AuthService authService;
    @Autowired UserService userService;

    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        try{
            return authService.registerUser(registerRequestDto);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        // Implement JWT authentication and return JWT token
        return null;
    }

    @GetMapping("/{_uuid}")
    ResponseEntity<UserDetailsDto> getUser(@PathVariable("_uuid") String uuid){
        return userService.findByUuid(uuid);
    }
}
