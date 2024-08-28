package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;

import java.util.List;

public interface CreditCardService {

    CreditCardResponseDto createCreditCard();

    CreditCardResponseDto getCreditCardByUuid(String uuid);

    CreditCardResponseDto updateCreditCard(String uuid, CreditCardRequestDto creditCardRequestDto);

    void deleteCreditCard(String uuid);

    List<CreditCardResponseDto> getAllCardsOfCurrentUser();
}
