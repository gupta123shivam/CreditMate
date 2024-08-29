package com.shivam.CreditMate.repository;

import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCardNumber(String cardNumber);

    List<Transaction> findByCreditCardAndTransactionDateBetween(CreditCard creditCard, LocalDateTime startDate, LocalDateTime endDate);
}
