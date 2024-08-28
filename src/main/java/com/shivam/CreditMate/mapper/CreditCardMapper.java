package com.shivam.CreditMate.mapper;

import org.mapstruct.Mapper;
import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import com.shivam.CreditMate.model.CreditCard;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {
    CreditCardResponseDto creditCardToCreditCardResponseDto(CreditCard creditCard);
}
