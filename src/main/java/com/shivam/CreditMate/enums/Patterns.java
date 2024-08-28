package com.shivam.CreditMate.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Patterns {
    static final String creditCardStatus = Stream.of(CreditCardStatus.values())
            .map(Enum::name)
            .collect(Collectors.joining("|"));

    static final String transactionRight = Stream.of(TransactionRight.values())
            .map(Enum::name)
            .collect(Collectors.joining("|"));
}
