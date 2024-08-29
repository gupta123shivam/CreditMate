package com.shivam.CreditMate.utils;

import jakarta.persistence.AttributeConverter;

public class Encrypt implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return EncryptionUtil.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return EncryptionUtil.decrypt(dbData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
