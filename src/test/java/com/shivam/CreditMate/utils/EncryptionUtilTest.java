package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.exception.exceptions.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EncryptionUtilTest {
    @Autowired
    private EncryptionUtil encryptionUtil;

    @Test
    void test() {

    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        String originalData = "myData";

        String encryptedData = encryptionUtil.encrypt(originalData);
        assertNotNull(encryptedData, "Encryption should not return null.");

        String decryptedData = encryptionUtil.decrypt(encryptedData);
        assertNotNull(decryptedData, "Decryption should not return null.");
        assertEquals(originalData, decryptedData, "Decrypted data should match the original data.");
    }

    @Test
    public void testDecrypt_ThrowsExceptionOnInvalidInput() {
        String invalidEncryptedData = "invalidEncryptedData";

        CustomException exception = assertThrows(CustomException.class, () ->
                encryptionUtil.decrypt(invalidEncryptedData), "Decrypt should throw an exception for invalid input.");
    }
}
