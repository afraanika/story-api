package com.cefalo.storyapi.services;

import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class UserDetailsServiceImpTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
    }

    @Test
    @DisplayName("Load User by UserName - Found")
    void testLoadUserByUsername() {
        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getUsername());

        UserDetails returnedUser = userDetailsServiceImp.loadUserByUsername(user.getUsername());

        assertTrue(Optional.of(returnedUser).isPresent(), "User Not Found");
        assertEquals(returnedUser, user, "Users should be equal");
    }

    @Test
    @DisplayName("Load User By Username - Not Found")
    void testLoadUserByUsernameNotFound() {
        doReturn(Optional.empty()).when(userRepository).findByEmail(user.getUsername());

        assertThrows(EntityNotFoundException.class, () ->
            userDetailsServiceImp.loadUserByUsername(user.getUsername()),
                "User Found, when it should not be");
    }
}
