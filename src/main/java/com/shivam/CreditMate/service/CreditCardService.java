package com.shivam.CreditMate.service;

import com.shivam.CreditMate.dto.request.CreditCardDto;
import com.shivam.CreditMate.model.CreditCard;

import java.util.List;

public interface CreditCardService {
    CreditCard createCreditCard(CreditCardDto creditCardDto);

    CreditCard updateCreditCard(Long id, CreditCardDto creditCardDto);

    CreditCard getCreditCardById(Long id);

    List<CreditCard> getCreditCardsByUserId(Long userId);

    void deleteCreditCard(Long id);
}
