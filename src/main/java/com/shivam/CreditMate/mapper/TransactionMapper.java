package com.shivam.CreditMate.mapper;

import com.shivam.CreditMate.dto.response.TransactionResponseDto;
import com.shivam.CreditMate.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponseDto transactionToTransactionResponseDto(Transaction transaction);
}
