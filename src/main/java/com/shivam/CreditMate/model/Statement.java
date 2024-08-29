package com.shivam.CreditMate.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "statements")

@Getter
@Setter
@Builder
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "statement_uuid")
    private String statementUuid;

    @Column(nullable = false, name = "card_number")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", nullable = false, updatable = false)
    private CreditCard creditCard;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, name = "transactions")
    private List<Transaction> transactions;

    private Double totalCredit;
    private Double totalDebit;
    private Double closingBalance;

    @PrePersist
    public void generateStatementUuid() {
        if (statementUuid == null) {
            statementUuid = java.util.UUID.randomUUID().toString();
        }
    }
}
