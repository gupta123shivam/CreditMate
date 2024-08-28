package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.CreditCardController;
import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.*;
import com.shivam.CreditMate.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CreditCardControllerimpl implements CreditCardController {
    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardControllerimpl(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public ResponseEntity<CreditCardResponseDto> createCreditCard() {
        CreditCardResponseDto response = creditCardService.createCreditCard();
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // HTTP 201 Created
    }

    @Override
    public ResponseEntity<CreditCardResponseDto> getCreditCardById(Long cardId) {
        CreditCardResponseDto response = creditCardService.getCreditCardById(cardId);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    @Override
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(Long cardId, CreditCardRequestDto creditCardRequestDto) {
        CreditCardResponseDto response = creditCardService.updateCreditCard(cardId, creditCardRequestDto);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    @Override
    public ResponseEntity<Void> deleteCreditCard(Long cardId) {
        creditCardService.deleteCreditCard(cardId);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    @Override
    public ResponseEntity<List<CreditCardResponseDto>> getAllCards() {
        List<CreditCardResponseDto> response = creditCardService.getAllCardsOfCurrentUser();
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    public ResponseEntity<Void> activateCard(Long cardId) {
        creditCardService.activateCard(cardId);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    public ResponseEntity<Void> deactivateCard(Long cardId) {
        creditCardService.deactivateCard(cardId);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
