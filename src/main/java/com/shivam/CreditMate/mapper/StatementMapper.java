package com.shivam.CreditMate.mapper;

import com.shivam.CreditMate.dto.response.StatementResponseDto;
import com.shivam.CreditMate.model.Statement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatementMapper {
    StatementResponseDto entityToResponse(Statement statement);
}
