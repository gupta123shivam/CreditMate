package com.shivam.CreditMate.repository;

import com.shivam.CreditMate.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByUserId(Long userId);

    Optional<CreditCard> findByUuid(String uuid);
}
