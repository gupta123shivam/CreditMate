package com.shivam.CreditMate.controller.impl;

import com.shivam.CreditMate.controller.CreditCardController;
import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import com.shivam.CreditMate.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementation of {@link CreditCardController} for managing credit card-related operations.
 */
@RestController
public class CreditCardControllerImpl implements CreditCardController {
    private final CreditCardService creditCardService;

    /**
     * Constructs an instance of {@link CreditCardControllerImpl}.
     *
     * @param creditCardService the credit card service
     */
    @Autowired
    public CreditCardControllerImpl(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    /**
     * Applies for a new credit card.
     *
     * @return a {@link ResponseEntity} with the created credit card response and HTTP status 201 (Created)
     */
    @Override
    public ResponseEntity<CreditCardResponseDto> applyForCreditCard() {
        CreditCardResponseDto response = creditCardService.createCreditCard();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves credit card details by card ID.
     *
     * @param cardId the ID of the credit card
     * @return a {@link ResponseEntity} with the credit card details and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<CreditCardResponseDto> getCreditCardDetails(Long cardId) {
        CreditCardResponseDto response = creditCardService.getCreditCardById(cardId);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates credit card details.
     *
     * @param cardId               the ID of the credit card to update
     * @param creditCardRequestDto the updated credit card details
     * @return a {@link ResponseEntity} with the updated credit card response and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<CreditCardResponseDto> updateCreditCardDetails(Long cardId, CreditCardRequestDto creditCardRequestDto) {
        CreditCardResponseDto response = creditCardService.updateCreditCard(cardId, creditCardRequestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a credit card by ID.
     *
     * @param cardId the ID of the credit card to delete
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @Override
    public ResponseEntity<Void> deleteCreditCard(Long cardId) {
        creditCardService.deleteCreditCard(cardId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all credit cards for the current user.
     *
     * @return a {@link ResponseEntity} with the list of all credit cards and HTTP status 200 (OK)
     */
    @Override
    public ResponseEntity<List<CreditCardResponseDto>> getAllCreditCards() {
        List<CreditCardResponseDto> response = creditCardService.getAllCardsOfCurrentUser();
        return ResponseEntity.ok(response);
    }

    /**
     * Activates a credit card by ID.
     *
     * @param cardId the ID of the credit card to activate
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    public ResponseEntity<Void> activateCard(Long cardId) {
        creditCardService.activateCard(cardId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deactivates a credit card by ID.
     *
     * @param cardId the ID of the credit card to deactivate
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    public ResponseEntity<Void> deactivateCard(Long cardId) {
        creditCardService.deactivateCard(cardId);
        return ResponseEntity.noContent().build();
    }
}
