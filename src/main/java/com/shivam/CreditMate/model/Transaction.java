package com.shivam.CreditMate.model;

import com.shivam.CreditMate.enums.TransactionCategory;
import com.shivam.CreditMate.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Column(name = "transaction_uuid", nullable = false, updatable = false)
    private final String transactionUuid = UUID.randomUUID().toString();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", nullable = false, updatable = false)
    private CreditCard creditCard;

    @Column(name = "card_number", nullable = false, updatable = false)
    private String cardNumber;

    @Column(name = "amount", nullable = false, updatable = false)
    private double amount;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_category")
    private TransactionCategory transactionCategory;

    @Column(name = "description")
    private String description = "";

    @Column(name = "transaction_date", updatable = false)
    private LocalDateTime transactionDate;

    @PrePersist
    void onCreate() {
        this.transactionDate = LocalDateTime.now();
    }
}
