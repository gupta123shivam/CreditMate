package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import com.shivam.CreditMate.enums.CreditCardStatus;
import com.shivam.CreditMate.enums.TransactionRight;
import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.mapper.CreditCardMapper;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.CreditCardRepository;
import com.shivam.CreditMate.service.CreditCardService;
import com.shivam.CreditMate.utils.CreditCardUtil;
import com.shivam.CreditMate.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;
    private final UserMapper userMapper;

    @Autowired
    public CreditCardServiceImpl(CreditCardRepository creditCardRepository, CreditCardMapper creditCardMapper, UserMapper userMapper) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardMapper = creditCardMapper;
        this.userMapper = userMapper;
    }

    @Override
    public CreditCardResponseDto createCreditCard() {
        // Get the logged-in user
        User user = UserUtil.getLoggedInUser();
        double creditLimit = CreditCardUtil.generateCreditLimit();
        String cardNumber = CreditCardUtil.generateCreditCardNumber();

        // Create and save a new credit card
        CreditCard creditCard = CreditCard.builder()
                .cardNumber(cardNumber)
                .user(user)
                .userUuid(user.getUuid())
                .creditLimit(creditLimit)
                .currentBalance(creditLimit)
                .status(CreditCardStatus.ACTIVE)
                .transactionRight(TransactionRight.VIEW_ONLY)
                .activated(false)
                .build();

        CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        return this.mapToResponseDto(savedCreditCard);
    }

    @Override
    public CreditCardResponseDto getCreditCardById(Long cardId) {
        // Retrieve and map credit card by ID
        CreditCard creditCard = CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, cardId);
        return this.mapToResponseDto(creditCard);
    }

    @Override
    public CreditCardResponseDto updateCreditCard(Long cardId, CreditCardRequestDto creditCardRequestDto) {
        // Find and update the credit card
        CreditCard creditCard = CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, cardId);

        creditCard.setCurrentBalance(creditCardRequestDto.getCurrentBalance());
        creditCard.setStatus(CreditCardStatus.valueOf(creditCardRequestDto.getStatus()));
        creditCard.setTransactionRight(TransactionRight.valueOf(creditCardRequestDto.getTransactionRight()));
        creditCard.setActivated(creditCardRequestDto.isActivated());

        CreditCard updatedCreditCard = creditCardRepository.save(creditCard);
        return mapToResponseDto(updatedCreditCard);
    }

    @Override
    public void deleteCreditCard(Long cardId) {
        // Validate and delete the credit card
        CreditCard creditCard = CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, cardId);
        if (creditCard.getCurrentBalance() < 0) throw new CustomException(AppErrorCodes.ERR_3003);
        creditCardRepository.delete(creditCard);
    }

    @Override
    public List<CreditCardResponseDto> getAllCardsOfCurrentUser() {
        // Retrieve and map all credit cards of the current user
        User loggedInUser = UserUtil.getLoggedInUser();
        Long currentUserId = loggedInUser.getId();
        List<CreditCard> creditCardList = creditCardRepository.findByUserId(currentUserId);
        return creditCardList.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void activateCard(Long cardId) {
        // Activate the credit card
        CreditCard creditCard = CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, cardId);
        creditCard.setActivated(true);
        creditCardRepository.save(creditCard);
    }

    @Override
    public void deactivateCard(Long cardId) {
        // Deactivate the credit card
        CreditCard creditCard = CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, cardId);
        creditCard.setActivated(false);
        creditCardRepository.save(creditCard);
    }

    // Convert CreditCard entity to CreditCardResponseDto
    private CreditCardResponseDto mapToResponseDto(CreditCard creditCard) {
        return creditCardMapper.creditCardToCreditCardResponseDto(creditCard);
    }
}
