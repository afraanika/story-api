package com.cefalo.storyapi.utills;

import com.cefalo.storyapi.utils.AuthenticationFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class AuthenticationFacadeTest {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setup() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
    }

//    @Test
//    @DisplayName("Test getAuthentication")
//    void testGetAuthentication() {
//        doReturn(authentication).when(securityContext).getAuthentication();
//
//        securityContext.setAuthentication(authentication);
//        Authentication returnedAuthentication = authenticationFacade.getAuthentication();
//
//        assertEquals(returnedAuthentication, authentication, "Authentication should be same");
//
//    }

}
