package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseEntity<UserDetailsDto> findByUuid(String uuid) {
        try{
            User user = userRepository.findByUuid(uuid).orElseThrow();
            return ResponseEntity.ok(userMapper.userToUserDetailsDto(user));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
