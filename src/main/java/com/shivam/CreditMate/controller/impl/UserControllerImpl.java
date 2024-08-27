package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.UserController;
import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {
    @Autowired UserService userService;

    @Override
    @GetMapping("/{_uuid}")
    public ResponseEntity<UserDetailsDto> getUser(@NotBlank @PathVariable("_uuid") String uuid){
        return userService.findByUuid(uuid);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserDetailsDto> getCurrentUser() {
        return null;
    }


}
