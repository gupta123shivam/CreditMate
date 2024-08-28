package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/credit-cards")
public interface CreditCardController {

    @PostMapping
    ResponseEntity<CreditCardResponseDto> createCreditCard();

    @GetMapping("/{cardId}")
    ResponseEntity<CreditCardResponseDto> getCreditCardById(@PathVariable("cardId") int cardId);

    @PutMapping("/{cardId}")
    ResponseEntity<CreditCardResponseDto> updateCreditCard(
            @PathVariable("cardId") int cardId,
            @NotNull @RequestBody CreditCardRequestDto creditCardRequestDto);

    @DeleteMapping("/{cardId}")
    ResponseEntity<Void> deleteCreditCard(@PathVariable("cardId") int cardId);

    @GetMapping("/all")
    ResponseEntity<List<CreditCardResponseDto>> getAllCards();
}
