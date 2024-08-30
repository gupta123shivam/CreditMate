package com.shivam.CreditMate.mapper;

import com.shivam.CreditMate.dto.UserDetailsDto;
import com.shivam.CreditMate.dto.response.LoginResponseDto;
import com.shivam.CreditMate.dto.response.RegisterResponseDto;
import com.shivam.CreditMate.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDetailsDto userToUserDetailsDto(User user);

    @Mapping(target = "jwtToken", ignore = true)
    LoginResponseDto userToLoginResponseDto(User user);

    RegisterResponseDto userToRegisterResponseDto(User user);
}
