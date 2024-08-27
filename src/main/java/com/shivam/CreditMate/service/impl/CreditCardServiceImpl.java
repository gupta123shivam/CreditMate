package com.shivam.CreditMate.service.impl;

import com.shivam.CreditMate.dto.request.CreditCardDto;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public CreditCard createCreditCard(CreditCardDto creditCardDto) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(creditCardDto.getCardNumber());
        creditCard.setUserId(creditCardDto.getUserId());
        creditCard.setCreditLimit(creditCardDto.getCreditLimit());
        creditCard.setCurrentBalance(creditCardDto.getCurrentBalance());
        creditCard.setStatus(creditCardDto.getStatus());
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard updateCreditCard(Long id, CreditCardDto creditCardDto) {
        CreditCard existingCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found"));
        existingCard.setCreditLimit(creditCardDto.getCreditLimit());
        existingCard.setCurrentBalance(creditCardDto.getCurrentBalance());
        existingCard.setStatus(creditCardDto.getStatus());
        return creditCardRepository.save(existingCard);
    }

    @Override
    public CreditCard getCreditCardById(Long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit card not found"));
    }

    @Override
    public List<CreditCard> getCreditCardsByUserId(Long userId) {
        return creditCardRepository.findByUserId(userId);
    }

    @Override
    public void deleteCreditCard(Long id) {
        creditCardRepository.deleteById(id);
    }
}