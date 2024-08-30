package com.shivam.CreditMate.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "statements")

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statement {

    @Builder.Default
    @Column(unique = true, nullable = false, name = "statement_uuid")
    private final String statementUuid = UUID.randomUUID().toString();

    @Builder.Default
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "card_number")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id", nullable = false, updatable = false)
    private CreditCard creditCard;

    @Column(nullable = false, name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDate endDate;

    @Lob
    @Column(nullable = false, name = "transactions_json")
    private String transactionsJson;

    private Double totalCredit;
    private Double totalDebit;
    private Double closingBalance;

    @Transient
    private List<Transaction> transactions;

    @PrePersist
    @PreUpdate
    public void serializeTransactions() {
        try {
            this.transactionsJson = objectMapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            throw new CustomException(AppErrorCodes.ERR_7004, e);
        }
    }

    @PostLoad
    public void deSerializeTransactions() {
        try {
            this.transactions = objectMapper.readValue(
                    transactionsJson,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));
        } catch (JsonProcessingException e) {
            throw new CustomException(AppErrorCodes.ERR_7005, e);
        }
    }
}
