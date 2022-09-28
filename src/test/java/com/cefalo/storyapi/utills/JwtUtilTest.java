package com.cefalo.storyapi.utills;

import com.cefalo.storyapi.services.UserDetailsServiceImp;
import com.cefalo.storyapi.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    private String email;

    private String token;

    @BeforeEach
    void setUp() {
        email = "bill@gmail.com";
        token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaWxsQGdtYWlsLmNvbSIsImlhdCI6MTY2NDM0MzM0MCwiZXhwIjoxNjY0MzYxMzQwfQ.w8lDuJXHiiZdmZubn_Mc2u10XgtHD8tYR-Or5pjNxKr97L0e4bZkf4bM82AJs3Td5Tkm6gcV8D5MMxIbeFByXg";
    }

    @Test
    @DisplayName("Test extractEmail - Success")
    void shouldReturnEmailFromToken() {
        String returnedEmail = jwtUtil.extractEmail(token);

        assertEquals(email, returnedEmail, "Emails should be equal");
    }

    @Test
    @DisplayName("Test extractExpirationDate - Success")
    void shouldReturnExpirationDateFromToken() {
        Date date = jwtUtil.extractExpirationDate(token);
        assertNotNull(date, "Date should not be null");
    }

    @Test
    @DisplayName("Test isTokenExpired - False")
    void shouldReturnFalse() {
        boolean isTokenExpired = jwtUtil.isTokenExpired(token);
        assertFalse(isTokenExpired);
    }

    @Test
    @DisplayName("Test createToken - Success")
    void shouldReturnGeneratedToken() {
        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);

        String returnedToken = jwtUtil.createToken(userDetails);

        assertNotNull(returnedToken, "Token should not be null");
    }

    @Test
    @DisplayName("Test validateToken - True")
    void shouldValidateToken() {
        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);

        Boolean isValid = jwtUtil.validateToken(token, userDetails);
        assertTrue(isValid, "Token should be valid");
    }
}
