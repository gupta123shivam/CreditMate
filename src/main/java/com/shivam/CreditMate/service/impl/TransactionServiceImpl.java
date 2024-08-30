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
import com.shivam.CreditMate.repository.UserRepository;
import com.shivam.CreditMate.service.TransactionService;
import com.shivam.CreditMate.utils.UserUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.shivam.CreditMate.utils.CreditCardUtil.getCreditCardByCardNumber;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public TransactionResponseDto doTransaction(TransactionRequestDto input) {
        // Retrieve credit card by card number
        CreditCard creditCard = getCreditCardByCardNumber(creditCardRepository, input.getCardNumber());

        // Create and build the transaction
        Transaction transaction = Transaction.builder()
                .cardNumber(creditCard.getCardNumber())
                .creditCard(creditCard)
                .amount(input.getAmount())
                .transactionType(TransactionType.valueOf(input.getTransactionType()))
                .description(input.getDescription())
                .transactionCategory(input.getTransactionCategory().isBlank() ? TransactionCategory.DEFAULT : TransactionCategory.valueOf(input.getTransactionCategory()))
                .build();

        // Handle business logic for credit and debit
        if ("DEBIT".equals(input.getTransactionType())) {
            if (creditCard.getCurrentBalance() < input.getAmount()) {
                throw new CustomException(AppErrorCodes.ERR_6001); // Insufficient balance
            }
            creditCard.setCurrentBalance(creditCard.getCurrentBalance() - input.getAmount());
        } else if ("CREDIT".equals(input.getTransactionType())) {
            creditCard.setCurrentBalance(creditCard.getCurrentBalance() + input.getAmount());
        }

        // Save updated credit card and transaction
        creditCardRepository.save(creditCard);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.transactionToTransactionResponseDto(savedTransaction);
    }

    @Override
    public TransactionResponseDto getTransactionById(Long id) {
        // Retrieve transaction by ID and verify access
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_6002)); // Transaction not found
        getCreditCardByCardNumber(creditCardRepository, transaction.getCardNumber()); // Verify user access
        return transactionMapper.transactionToTransactionResponseDto(transaction);
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByCardNumber(String cardNumber, Long limit) {
        // Retrieve transactions for a specific card number
        CreditCard creditCard = getCreditCardByCardNumber(creditCardRepository, cardNumber);
        List<Transaction> transactions = creditCard.getTransactions();
        return transactions.stream()
                .limit(limit)
                .map(transactionMapper::transactionToTransactionResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<List<TransactionResponseDto>> getAllTransactions(Long limit) {
        User currentUser = UserUtil.getLoggedInUser();
        List<CreditCard> creditCards = getCreditCardsForUser(currentUser.getId());
        return creditCards.stream()
                .map(card -> getTransactionsByCardNumber(card.getCardNumber(), limit))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CreditCard> getCreditCardsForUser(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(AppErrorCodes.ERR_2002));
        return currentUser.getCreditCards();
    }
}
