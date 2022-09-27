package com.cefalo.storyapi.services;

import com.cefalo.storyapi.exceptions.EmailNotUniqueException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.exceptions.IncorrectEmailOrPasswordException;
import com.cefalo.storyapi.exceptions.PasswordNotValidException;
import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.PasswordValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
public class AuthServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidationUtil passwordValidationUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    private User user;


    private User userWithEncodedPassword;

	@BeforeEach
	public void setup(){
        user = new User("Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
        userWithEncodedPassword = new User("Billy", "01236547893", "bill@gmail.com", "sadasAsa");

    }

    @Test
    @DisplayName("Test ValidateUSer - Success")
    void shouldReturnJwtTokenAfterSignIn() {
        doReturn(Optional.of(userWithEncodedPassword)).when(userRepository).findByEmail(user.getEmail());
        doReturn(true).when(passwordEncoder).matches(user.getPassword(), userWithEncodedPassword.getPassword());

        JwtResponse mockJwtResponse = jwtService.authenticate(user);
        JwtResponse jwtResponse = authService.validateUser(user);

        assertEquals(mockJwtResponse, jwtResponse, "Jwt Token should be equal");
    }

    @Test
    @DisplayName("Test ValidateUser - NotFound")
    void shouldThrowEntityNotFoundException() {
        doReturn(Optional.empty()).when(userRepository).findByEmail(user.getEmail());

        assertThrows(EntityNotFoundException.class,
                () -> authService.validateUser(user),
                "Found User, when it should noe be");
    }

    @Test
    @DisplayName("Test Validate - IncorrectEmailOrPassword")
    void shouldThrowIncorrectEmailOrPasswordException() {
        doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());
        doReturn(false).when(passwordEncoder).matches(user.getPassword(), userWithEncodedPassword.getPassword());

        assertThrows(IncorrectEmailOrPasswordException.class,
                () -> authService.validateUser(user),
                "Should contain Incorrect Email or Password");
    }

    @Test
    @DisplayName("Test AddUser - Success")
    void shouldReturnJwtTokenAfterSignup() {
        String encodedPassword = "aE3fSaP2a";
        doReturn(userWithEncodedPassword).when(userRepository).save(user);
        doReturn(encodedPassword).when(passwordEncoder).encode(user.getPassword());

        JwtResponse mockJwtResponse = jwtService.authenticate(user);
        JwtResponse jwtResponse = authService.addUser(user);

        assertEquals(mockJwtResponse, jwtResponse, "Jwt Token should be equal");
    }

    @Test
    @DisplayName("Test AddUser - EmailNotUnique")
	void shouldThrowUserEmailNotUniqueException() {
		doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

		assertThrows(EmailNotUniqueException.class, () ->
			authService.addUser(user)
		, "User Not Found, when it should be");
	}

    @Test
    @DisplayName("Test AddUser - PasswordNotValid")
    void shouldThrowPasswordNotValidException() {
        doReturn(false).when(passwordValidationUtil).passwordValidator(userWithEncodedPassword.getPassword());

        assertThrows(PasswordNotValidException.class, () ->
                        authService.addUser(userWithEncodedPassword)
                , "Valid Password, when it should not be");
    }

}
