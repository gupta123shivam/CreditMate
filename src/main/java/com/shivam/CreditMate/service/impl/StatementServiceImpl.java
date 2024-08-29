package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.StatementRequestDto;
import com.shivam.CreditMate.dto.response.StatementResponseDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import com.shivam.CreditMate.enums.TransactionType;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.mapper.StatementMapper;
import com.shivam.CreditMate.mapper.TransactionMapper;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.model.Statement;
import com.shivam.CreditMate.model.Transaction;
import com.shivam.CreditMate.repository.CreditCardRepository;
import com.shivam.CreditMate.repository.StatementRepository;
import com.shivam.CreditMate.repository.TransactionRepository;
import com.shivam.CreditMate.service.StatementService;
import com.shivam.CreditMate.utils.CreditCardUtil;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementServiceImpl implements StatementService {

    private final StatementRepository statementRepository;
    private final TransactionRepository transactionRepository;
    private final CreditCardRepository creditCardRepository;
    private final StatementMapper statementMapper;
    private final TransactionMapper transactionMapper;

    @Autowired
    public StatementServiceImpl(StatementRepository statementRepository, TransactionRepository transactionRepository, CreditCardRepository creditCardRepository, StatementMapper statementMapper, TransactionMapper transactionMapper) {
        this.statementRepository = statementRepository;
        this.transactionRepository = transactionRepository;
        this.creditCardRepository = creditCardRepository;
        this.statementMapper = statementMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public StatementResponseDto generateStatement(StatementRequestDto input) {
        String cardNumber = input.getCardNumber();
        LocalDate startDate = input.getStartDate();
        LocalDate endDate = input.getEndDate();

        CreditCard creditCard = CreditCardUtil.getCreditCardByCardNumber(creditCardRepository, cardNumber);
        List<Transaction> transactions = transactionRepository
                .findByCreditCardAndTransactionDateBetween(creditCard, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Initialize totals for credit and debit.
        double totalCredit = 0.0;
        double totalDebit = 0.0;

        // Loop through the transactions and calculate totals.
        for (Transaction t : transactions) {
            if (t.getTransactionType() == TransactionType.CREDIT) {
                totalCredit += t.getAmount();
            } else if (t.getTransactionType() == TransactionType.DEBIT) {
                totalDebit += t.getAmount();
            }
        }

        double closingBalance = totalCredit - totalDebit; // Adjust based on initial balance if needed

        Statement statement = Statement.builder()
                .cardNumber(cardNumber)
                .startDate(startDate)
                .endDate(endDate)
                .transactions(transactions)
                .totalCredit(totalCredit)
                .totalDebit(totalDebit)
                .closingBalance(closingBalance)
                .build();

        Statement savedStatement = statementRepository.save(statement);

        return mapToResponseDto(savedStatement);
    }

    @Override
    public StatementResponseDto getStatementByUuid(String statementUuid) {
        Statement statement = statementRepository.findByStatementUuid(statementUuid)
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_7001));
        String currentUserUuid = UserUtil.getLoggedInUser().getUuid();
        if (!currentUserUuid.equals(statement.getCreditCard().getUserUuid())) {
            throw new CustomException(AppErrorCodes.ERR_7002);
        }

        return mapToResponseDto(statement);
    }

    @Override
    public StatementResponseDto getLatestStatement(String cardNumber) {
        CreditCard creditCard = CreditCardUtil.getCreditCardByCardNumber(creditCardRepository, cardNumber);
        List<Statement> statements = creditCard.getStatements();
        if (statements.isEmpty()) throw new CustomException(AppErrorCodes.ERR_7003);
        return mapToResponseDto(statements.get(0));
    }

    private StatementResponseDto mapToResponseDto(Statement statement) {
        List<TransactionResponseDto> transactionDtos = statement.getTransactions().stream()
                .map(transactionMapper::transactionToTransactionResponseDto)
                .collect(Collectors.toList());

        StatementResponseDto statementResponseDto = statementMapper.entityToResponse(statement);
        statementResponseDto.setTransactions(transactionDtos);
        return statementResponseDto;
    }
}
