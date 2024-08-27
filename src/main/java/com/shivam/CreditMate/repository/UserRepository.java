package com.shivam.CreditMate.repository;

import com.shivam.CreditMate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUuid(String uuid);

    Optional<User> findByEmail(String email);
}
