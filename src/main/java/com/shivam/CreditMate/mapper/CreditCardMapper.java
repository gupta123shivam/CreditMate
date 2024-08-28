package com.shivam.CreditMate.mapper;

import com.shivam.CreditMate.dto.response.CreditCardResponseDto;
import com.shivam.CreditMate.model.CreditCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {
    CreditCardResponseDto creditCardToCreditCardResponseDto(CreditCard creditCard);
}
