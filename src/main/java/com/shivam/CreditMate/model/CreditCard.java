package com.shivam.CreditMate.model;

import com.shivam.CreditMate.enums.CreditCardStatus;
import com.shivam.CreditMate.enums.TransactionRight;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_cards")

@Getter
@Setter
@Builder
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String userUuid;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "current_balance")
    private Double currentBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CreditCardStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_right")
    private TransactionRight transactionRight;

    @Column
    private boolean activated;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.uuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = CreditCardStatus.BLOCKED;
        this.transactionRight = TransactionRight.VIEW_ONLY;
        this.activated = false;
    }
}
