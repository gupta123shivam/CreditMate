package com.shivam.CreditMate.repository;

import com.shivam.CreditMate.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatementRepository extends JpaRepository<Statement, Long> {
    Optional<Statement> findByStatementUuid(String statementUuid);
}
