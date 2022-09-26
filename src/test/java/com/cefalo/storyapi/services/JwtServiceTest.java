package com.cefalo.storyapi.services;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsServiceImp userDetailsServiceImp;

    private User user;

    private String token;

    private JwtResponse jwtResponse;


    @BeforeEach
    void setup() {
        user =  new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaWxsQGdtYWlsLmNvbSIsImlhdCI6MTY2MzgyNjY3OCwiZXhwIjoxNjYzODYyNjc4fQ.i7aH_-_HICz7TWeVobrv18RvazEmbyLH8QTKXjG8kAQ";
        jwtResponse = new JwtResponse(token);
    }

//    @Test
//    @DisplayName("Test Authenticate")
//    void testAuthenticate() {
//        doReturn(token).when(jwtUtil).generateToken(user);
//
//        JwtResponse jwtResponseFromUtil = new JwtResponse(jwtUtil.generateToken(user));
//        JwtResponse jwtResponseFromAuthentication = new JwtResponse()
//
//    }

}
