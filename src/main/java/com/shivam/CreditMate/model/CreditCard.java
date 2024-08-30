package com.shivam.CreditMate.model;

import com.shivam.CreditMate.enums.CreditCardStatus;
import com.shivam.CreditMate.enums.TransactionRight;
import com.shivam.CreditMate.utils.Encrypt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    @Builder.Default
    @Column(name = "uuid", unique = true, nullable = false)
    private final String uuid = UUID.randomUUID().toString();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Convert(converter = Encrypt.class)
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    // Field to store the encrypted card number
//    @Column(name = "encrypted_card_number", nullable = false)
//    private String encryptedCardNumber;

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

    @Builder.Default
    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions = Collections.emptyList();

    @Builder.Default
    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Statement> statements = Collections.emptyList();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

//    @PrePersist
//    @PreUpdate
//    private void encryptSensitiveData() {
//        try {
//            this.encryptedCardNumber = EncryptionUtil.encrypt(this.cardNumber);
//        } catch (Exception e) {
//            throw new CustomException(AppErrorCodes.ERR_8001, e);
//        }
//    }

//    @PostLoad
//    private void decryptSensitiveData() {
//        try {
//            this.cardNumber = EncryptionUtil.decrypt(this.encryptedCardNumber);
//        } catch (Exception e) {
//            throw new CustomException(AppErrorCodes.ERR_8002, e);
//        }
//    }
}