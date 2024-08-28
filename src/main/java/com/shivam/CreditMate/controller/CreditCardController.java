package com.shivam.CreditMate.controller;

import com.shivam.CreditMate.dto.request.CreditCardRequestDto;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/credit-cards")
public interface CreditCardController {

    @PostMapping
    ResponseEntity<CreditCardResponseDto> createCreditCard();

    @GetMapping("/{uuid}")
    ResponseEntity<CreditCardResponseDto> getCreditCardById(@NotBlank @PathVariable("uuid") String uuid);

    @PutMapping("/{uuid}")
    ResponseEntity<CreditCardResponseDto> updateCreditCard(
            @NotBlank @PathVariable("uuid") String uuid,
            @NotNull @RequestBody CreditCardRequestDto creditCardRequestDto);

    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> deleteCreditCard(@NotBlank @PathVariable("uuid") String uuid);

    @GetMapping("/all")
    ResponseEntity<List<CreditCardResponseDto>> getAllCards();
}
