package com.shivam.CreditMate.utils;

import com.shivam.CreditMate.enums.Role;
import com.shivam.CreditMate.exception.exceptions.AuthException.InvalidCredentialsException;
import com.shivam.CreditMate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUtilTest {

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
    }

    @Test
    public void testConvertStringToRole_Valid() {
        Role role = UserUtil.convertStringToRole("role_admin");
        assertEquals(Role.ROLE_ADMIN, role);
    }

    @Test
    public void testConvertStringToRole_Invalid() {
        assertThrows(RuntimeException.class, () -> UserUtil.convertStringToRole("unknownRole"));
    }

    @Test
    public void testGetLoggedInUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(testUser);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            User result = UserUtil.getLoggedInUser();
            assertEquals(testUser, result);
        }
    }

    @Test
    public void testGetLoggedInUser_NoUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(null); // no context is set yet

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        try (MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            assertThrows(InvalidCredentialsException.class, UserUtil::getLoggedInUser);
        }
    }
}
