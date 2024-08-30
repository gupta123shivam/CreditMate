package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller interface for managing credit card operations.
 * Provides endpoints for applying, viewing, updating, deleting, and managing credit cards.
 */
@PreAuthorize("isAuthenticated() and hasRole('ROLE_OWNER')")
@RequestMapping("/api/credit-cards")
public interface CreditCardController {

    /**
     * Applies for a new credit card.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @return a ResponseEntity containing the credit card response details
     */
    @PostMapping("/apply")
    ResponseEntity<CreditCardResponseDto> applyForCreditCard();

    /**
     * Retrieves details of a specific credit card.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @param cardId the (Long id as in DB) ID of the credit card
     * @return a ResponseEntity containing the credit card response details
     */
    @GetMapping("/{cardId}")
    ResponseEntity<CreditCardResponseDto> getCreditCardDetails(@PathVariable("cardId") Long cardId);

    /**
     * Updates details of a specific credit card.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @param cardId               the ID of the credit card
     * @param creditCardRequestDto the updated credit card details
     * @return a ResponseEntity containing the updated credit card response details
     */
    @PutMapping("/{cardId}")
    ResponseEntity<CreditCardResponseDto> updateCreditCardDetails(
            @PathVariable("cardId") Long cardId,
            @NotNull @RequestBody CreditCardRequestDto creditCardRequestDto);

    /**
     * Deletes a specific credit card.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @param cardId the ID of the credit card to be deleted
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{cardId}")
    ResponseEntity<Void> deleteCreditCard(@PathVariable("cardId") Long cardId);

    /**
     * Retrieves a list of all credit cards for the authenticated user.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @return a ResponseEntity containing a list of credit card response details
     */
    @GetMapping("/all-cards")
    ResponseEntity<List<CreditCardResponseDto>> getAllCreditCards();

    /**
     * Activates a specific credit card.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @param cardId the ID of the credit card to be activated
     * @return a ResponseEntity with no content
     */
    @PutMapping("/{cardId}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable Long cardId);

    /**
     * Deactivates a specific credit card.
     * Accessible only by authenticated users with the ROLE_OWNER role.
     *
     * @param cardId the ID of the credit card to be deactivated
     * @return a ResponseEntity with no content
     */
    @PutMapping("/{cardId}/deactivate")
    public ResponseEntity<Void> deactivateCard(@PathVariable Long cardId);
}
