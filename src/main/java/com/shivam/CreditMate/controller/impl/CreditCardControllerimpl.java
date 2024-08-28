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
    public ResponseEntity<CreditCardResponseDto> getCreditCardById(int cardId) {
        try {
            CreditCardResponseDto response = creditCardService.getCreditCardById((long) cardId);
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (CreditCardDoesNotExist | UserNotAuthorizedForThisCreditCard e) {
            throw e; // Custom exceptions are handled by the GlobalExceptionHandler
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving credit card", e);
        }
    }

    @Override
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(int cardId, CreditCardRequestDto creditCardRequestDto) {
        try {
            CreditCardResponseDto response = creditCardService.updateCreditCard((long) cardId, creditCardRequestDto);
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (CreditCardDoesNotExist | UserNotAuthorizedForThisCreditCard e) {
            throw e; // Custom exceptions are handled by the GlobalExceptionHandler
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating credit card", e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteCreditCard(int cardId) {
        try {
            creditCardService.deleteCreditCard((long) cardId);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (CreditCardDoesNotExist | UserNotAuthorizedForThisCreditCard e) {
            throw e; // Custom exceptions are handled by the GlobalExceptionHandler
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting credit card", e);
        }
    }

    @Override
    public ResponseEntity<List<CreditCardResponseDto>> getAllCards() {
        try {
            List<CreditCardResponseDto> response = creditCardService.getAllCardsOfCurrentUser();
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving credit cards", e);
        }
    }
}
