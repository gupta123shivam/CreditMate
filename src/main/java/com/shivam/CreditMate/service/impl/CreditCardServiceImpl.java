package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import com.shivam.CreditMate.mapper.CreditCardMapper;
import com.shivam.CreditMate.mapper.UserMapper;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.enums.CreditCardStatus;
import com.shivam.CreditMate.enums.TransactionRight;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.CreditCardRepository;
import com.shivam.CreditMate.service.CreditCardService;
import com.shivam.CreditMate.utils.CreditCardUtil;
import com.shivam.CreditMate.utils.UserUtil;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.*;
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
        // Retrieve the logged-in user
        User user = UserUtil.getLoggedInUser();
        double creditLimit = CreditCardUtil.generateCreditLimit();
        String cardNumber = CreditCardUtil.generateCreditCardNumber();

        // Create a new credit card instance
        CreditCard creditCard = CreditCard.builder()
                .cardNumber(cardNumber)  // Implement this method to generate a valid card number
                .user(user)  // Set the user
                .userUuid(user.getUuid())
                .creditLimit(creditLimit)  // Set the random credit limit
                .currentBalance(creditLimit)  // Initial balance is zero
                .status(CreditCardStatus.ACTIVE)  // Set default status
                .transactionRight(TransactionRight.VIEW_ONLY)  // Default transaction right
                .activated(true)  // Set default activated state
                .build();

        // Save the credit card to the database
        CreditCard savedCreditCard = creditCardRepository.save(creditCard);
        return this.mapToResponseDto(savedCreditCard);
    }

    @Override
    public CreditCardResponseDto getCreditCardByUuid(String uuid) {
        CreditCard creditCard = findByUuidAndUserUuid(uuid);
        return this.mapToResponseDto(creditCard);
    }

    // TODO for now we are making it simple and expecting all the fields for update. later on this
    // can be refined to update individual field only
    @Override
    public CreditCardResponseDto updateCreditCard(String uuid, CreditCardRequestDto creditCardRequestDto) {
        CreditCard creditCard = findByUuidAndUserUuid(uuid);

        creditCard.setCurrentBalance(creditCardRequestDto.getCurrentBalance());
        creditCard.setStatus(CreditCardStatus.valueOf(creditCardRequestDto.getStatus()));
        creditCard.setTransactionRight(TransactionRight.valueOf(creditCardRequestDto.getTransactionRight()));
        creditCard.setActivated(creditCardRequestDto.isActivated());

        CreditCard updatedCreditCard = creditCardRepository.save(creditCard);

        return mapToResponseDto(updatedCreditCard);
    }

    @Override
    public void deleteCreditCard(String uuid) {
        CreditCard creditCard = findByUuidAndUserUuid(uuid);
        creditCardRepository.delete(creditCard);
    }

    @Override
    public List<CreditCardResponseDto> getAllCardsOfCurrentUser() {
        User loggedInUser = UserUtil.getLoggedInUser();
        Long currentUserId = loggedInUser.getId();
        List<CreditCard> creditCardList = creditCardRepository.findByUserId(currentUserId);
        return creditCardList.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // checking is current user is authorized to perform action or not
    CreditCard findByUuidAndUserUuid(String uuid) {
        CreditCard creditCard = creditCardRepository.findByUuid(uuid)
                .orElseThrow(CreditCardDoesNotExist::new);
        if (!creditCard.getUserUuid().equals(UserUtil.getLoggedInUser().getUuid()))
            throw new UserNotAuthorizedForThisCreditCard();
        else return creditCard;
    }

    // Helper method to map entity to DTO
    CreditCardResponseDto mapToResponseDto(CreditCard creditCard) {
        CreditCardResponseDto creditCardResponseDto = creditCardMapper.creditCardToCreditCardResponseDto(creditCard);
        return creditCardResponseDto;
    }
}
