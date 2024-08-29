package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.TransactionRequestDto;
import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import com.shivam.CreditMate.enums.TransactionCategory;
import com.shivam.CreditMate.enums.TransactionType;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.mapper.TransactionMapper;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.model.Transaction;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.CreditCardRepository;
import com.shivam.CreditMate.repository.TransactionRepository;
import com.shivam.CreditMate.service.TransactionService;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.shivam.CreditMate.utils.CreditCardUtil.getCreditCardByCardNumber;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public TransactionResponseDto doTransaction(TransactionRequestDto input) {
        CreditCard creditCard = getCreditCardByCardNumber(creditCardRepository, input.getCardNumber());

        Transaction transaction = Transaction.builder()
                .cardNumber(creditCard.getCardNumber())
                .creditCard(creditCard)
                .amount(input.getAmount())
                .transactionType(TransactionType.valueOf(input.getTransactionType()))
                .description(input.getDescription())
                .transactionCategory(TransactionCategory.DEFAULT)
                .build();

        if (!input.getTransactionCategory().isBlank())
            transaction.setTransactionCategory(TransactionCategory.valueOf(input.getTransactionCategory()));

        // Handle business logic for credit and debit
        if ("DEBIT".equals(input.getTransactionType())) {
            if (creditCard.getCurrentBalance() < input.getAmount()) {
                throw new CustomException(AppErrorCodes.ERR_6001);
            }
            creditCard.setCurrentBalance(creditCard.getCurrentBalance() - input.getAmount());
        } else if ("CREDIT".equals(input.getTransactionType())) {
            creditCard.setCurrentBalance(creditCard.getCurrentBalance() + input.getAmount());
        }

        creditCardRepository.save(creditCard);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.transactionToTransactionResponseDto(savedTransaction);
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new CustomException(AppErrorCodes.ERR_6002));
        getCreditCardByCardNumber(creditCardRepository, transaction.getCardNumber()); // for checking if current user has access to this transaction or not
        return transactionMapper.transactionToTransactionResponseDto(transaction);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByCardNumber(String cardNumber) {
        CreditCard creditCard = getCreditCardByCardNumber(creditCardRepository, cardNumber);
        List<Transaction> transactions = creditCard.getTransactions();
        return transactions.stream()
                .map(t -> transactionMapper.transactionToTransactionResponseDto(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<List<TransactionResponseDto>> getAllTransactions(Long limit) {
        if (limit <= 0) limit = 10L;

        User currentuser = UserUtil.getLoggedInUser();
        List<CreditCard> creditCards = currentuser.getCreditCards();
        List<List<TransactionResponseDto>> listOfTransactions = creditCards
                .stream()
                .limit(limit)
                .map(card -> getTransactionsByCardNumber(card.getCardNumber()))
                .toList();
        return listOfTransactions;
    }
}
