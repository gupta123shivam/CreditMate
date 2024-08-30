package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.exception.AppErrorCodes;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.CreditCardDoesNotExist;
import com.shivam.CreditMate.exception.exceptions.CreditCardException.UserNotAuthorizedForThisCreditCard;
import com.shivam.CreditMate.exception.exceptions.CustomException;
import com.shivam.CreditMate.model.CreditCard;
import com.shivam.CreditMate.model.User;
import com.shivam.CreditMate.repository.CreditCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditCardUtilTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardUtil creditCardUtil;

    private User testUser;
    private CreditCard testCreditCard;

    @BeforeEach
    public void setUp() {
        testUser = new User();

        testCreditCard = new CreditCard();
        testCreditCard.setId(1L);
        testCreditCard.setUserUuid(testUser.getUuid());
        testCreditCard.setCardNumber("4111111111111111");
    }

    @Test
    public void testGenerateCreditLimit() {
        double limit = CreditCardUtil.generateCreditLimit();
        assertTrue(limit >= 10.0 && limit <= 100.0);
    }

    @Test
    public void testGenerateCreditCardNumber() {
        String cardNumber = CreditCardUtil.generateCreditCardNumber();
        assertEquals(16, cardNumber.length());
        assertTrue(cardNumber.startsWith("4"));  // assuming Visa cards
    }

    @Test
    public void testFindByIdAndCurrentUser_CardExistsAndAuthorized() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(testCreditCard));

        try (MockedStatic<UserUtil> mockedUserUtil = mockStatic(UserUtil.class)) {
            mockedUserUtil.when(UserUtil::getLoggedInUser).thenReturn(testUser);
            CreditCard result = CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, 1L);
            assertEquals(testCreditCard, result);
        }
    }

    @Test
    public void testFindByIdAndCurrentUser_CardDoesNotExist() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CreditCardDoesNotExist.class, () ->
                CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, 1L));
    }

    @Test
    public void testFindByIdAndCurrentUser_NotAuthorized() {
        testCreditCard.setUserUuid("other-uuid");
        when(creditCardRepository.findById(1L)).thenReturn(Optional.of(testCreditCard));

        try (MockedStatic<UserUtil> mockedUserUtil = mockStatic(UserUtil.class)) {
            mockedUserUtil.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            assertThrows(UserNotAuthorizedForThisCreditCard.class, () ->
                    CreditCardUtil.findByIdAndCurrentUser(creditCardRepository, 1L));
        }
    }

    @Test
    public void testGetCreditCardByCardNumber_CardExistsAndBelongsToUser() {
        when(creditCardRepository.findByCardNumber("4111111111111111")).thenReturn(Optional.of(testCreditCard));

        try (MockedStatic<UserUtil> mockedUserUtil = mockStatic(UserUtil.class)) {
            mockedUserUtil.when(UserUtil::getLoggedInUser).thenReturn(testUser);
            CreditCard result = CreditCardUtil.getCreditCardByCardNumber(creditCardRepository, "4111111111111111");
            assertEquals(testCreditCard, result);
        }
    }

    @Test
    public void testGetCreditCardByCardNumber_CardDoesNotExist() {
        when(creditCardRepository.findByCardNumber("4111111111111111")).thenReturn(Optional.empty());

        try (MockedStatic<UserUtil> mockedUserUtil = mockStatic(UserUtil.class)) {
            mockedUserUtil.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            CustomException exception = assertThrows(CustomException.class, () ->
                    CreditCardUtil.getCreditCardByCardNumber(creditCardRepository, "4111111111111111"));

            assertEquals(AppErrorCodes.ERR_3001, exception.getError());
        }
    }

    @Test
    public void testGetCreditCardByCardNumber_NotOwner() {
        testCreditCard.setUserUuid("other-uuid");
        when(creditCardRepository.findByCardNumber("4111111111111111")).thenReturn(Optional.of(testCreditCard));

        try (MockedStatic<UserUtil> mockedUserUtil = mockStatic(UserUtil.class)) {
            mockedUserUtil.when(UserUtil::getLoggedInUser).thenReturn(testUser);

            CustomException exception = assertThrows(CustomException.class, () ->
                    CreditCardUtil.getCreditCardByCardNumber(creditCardRepository, "4111111111111111"));

            assertEquals(AppErrorCodes.ERR_3002, exception.getError());
        }
    }
}
