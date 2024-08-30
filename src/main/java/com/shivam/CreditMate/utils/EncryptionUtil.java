package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public final class EncryptionUtil {
    @Value("${app-encryption.algo}")
    private String ALGO;

    @Value("${app-encryption.secret-key}")
    private String SECRET_KEY;

    /**
     * Method to encrypt data
     */
    public String encrypt(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGO);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new CustomException(AppErrorCodes.ERR_8003, e);
        }
    }

    /**
     * Method to decrypt data
     */
    public String decrypt(String encryptedData) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGO);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (Exception e) {
            throw new CustomException(AppErrorCodes.ERR_8004, e);
        }
    }
}
