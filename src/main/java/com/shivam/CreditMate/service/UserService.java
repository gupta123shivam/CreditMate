package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.PasswordChangeDto;
import com.shivam.CreditMate.dto.request.UserProfileUpdateRequestDto;
import com.shivam.CreditMate.model.User;

public interface UserService {
    User findByUuid(String uuid);

    User loadUserByEmail(String email);

    User loadUserByUsername(String username);

    User updateUserProfile(UserProfileUpdateRequestDto userProfileUpdateRequestDto);

    void updatePassword(PasswordChangeDto passwordChangeDto);
}
