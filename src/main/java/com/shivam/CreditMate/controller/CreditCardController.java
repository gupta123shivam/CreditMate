package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/credit-cards")
public interface CreditCardController {

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @PostMapping("/apply")
    ResponseEntity<CreditCardResponseDto> applyForCreditCard();

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @GetMapping("/{cardId}")
    ResponseEntity<CreditCardResponseDto> getCreditCardDetails(@PathVariable("cardId") Long cardId);

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @PutMapping("/{cardId}")
    ResponseEntity<CreditCardResponseDto> updateCreditCardDetails(
            @PathVariable("cardId") Long cardId,
            @NotNull @RequestBody CreditCardRequestDto creditCardRequestDto);

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @DeleteMapping("/{cardId}")
    ResponseEntity<Void> deleteCreditCard(@PathVariable("cardId") Long cardId);

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @GetMapping("/all-cards")
    ResponseEntity<List<CreditCardResponseDto>> getAllCreditCards();

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @PutMapping("/{cardId}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable Long cardId);

    @PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @PutMapping("/{cardId}/deactivate")
    public ResponseEntity<Void> deactivateCard(@PathVariable Long cardId);
}
