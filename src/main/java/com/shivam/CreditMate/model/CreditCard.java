package com.shivam.CreditMate.model;

import com.shivam.CreditMate.enums.CreditCardStatus;
import com.shivam.CreditMate.enums.TransactionRight;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_uuid", nullable = false)
    private String userUuid;

    @Column(name = "credit_limit", nullable = false)
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

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.uuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}