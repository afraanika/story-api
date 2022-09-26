package com.cefalo.storyapi.services;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.AuthenticationFacade;
import com.cefalo.storyapi.utils.IAuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurrentUserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private CurrentUserService currentUserService;

    private Authentication authentication;

    private User user;

    @BeforeEach
    void setUp() {
        user =  new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
    }

    @Test
    @DisplayName("Test GetCurrentUserEmail - Found")
    void shouldReturnCurrentUserEmail() {
        authentication = authenticationFacade.getAuthentication();
        String email = authentication.getName();

        assertEquals(email, user.getEmail(), "Emails should be equal");
    }

    @Test
    @DisplayName("Test GetCurrentUserEmail - NotAuthenticated")
    void shouldReturnCurrentUserEmailNotAuthenticated() {
        authentication = authenticationFacade.getAuthentication();

        assertThrows(AccessDeniedException.class, () ->
            authentication.isAuthenticated(),
                    "User should not be authenticated");
    }
}