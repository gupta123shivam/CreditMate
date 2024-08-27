package com.shivam.CreditMate.service;

import com.shivam.CreditMate.model.User;

public interface UserService {
    User findByUuid(String uuid);

    User loadUserByEmail(String email);

    User loadUserByUsername(String username);
}
