package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;

import java.util.List;

public interface CreditCardService {

    CreditCardResponseDto createCreditCard();

    CreditCardResponseDto getCreditCardById(Long cardId);

    CreditCardResponseDto updateCreditCard(Long cardId, CreditCardRequestDto creditCardRequestDto);

    void deleteCreditCard(Long cardId);

    List<CreditCardResponseDto> getAllCardsOfCurrentUser();
}
