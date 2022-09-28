package com.cefalo.storyapi.services;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.AuthenticationFacade;
import com.cefalo.storyapi.utils.IAuthenticationFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Security;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CurrentUserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private AuthenticationFacade authenticationFacade;

    @Autowired
    private CurrentUserService currentUserService;

    private Authentication authentication;

//    @Test
//    @DisplayName("Test GetCurrentUserEmail - Found")
//    void shouldReturnCurrentUserEmail() {
//        User user =  new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
//
//        authentication = authenticationFacade.getAuthentication();
//        String email = authentication.getName();
//        currentUserService.getUser();
//        assertEquals(email, user.getEmail(), "Emails should be equal");
//    }
//
//    @Test
//    @DisplayName("Test GetCurrentUserEmail - NotAuthenticated")
//    void shouldReturnCurrentUserEmailNotAuthenticated() {
//
//        assertThrows(AccessDeniedException.class, () ->
//            authenticationFacade.getAuthentication().isAuthenticated(),
//                    "User should not be authenticated");
//    }
}