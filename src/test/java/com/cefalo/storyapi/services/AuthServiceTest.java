package com.cefalo.storyapi.services;

import com.cefalo.storyapi.exceptions.EmailNotUniqueException;
import com.cefalo.storyapi.exceptions.PasswordNotValidException;
import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.PasswordValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@SpringBootTest
public class AuthServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PasswordValidationUtil passwordValidationUtil;

    @Autowired
    private AuthService authService;

    private User user;

	@BeforeEach
	public void setup(){
		user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
    }

//    @Test
//    @DisplayName("Test AddUser - Success")
//    void testAddUser() {
//        doReturn(user).when(userRepository).save(user);
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        JwtResponse jwtResponse = authService.addUser(user);
//
//        assertEquals(jwtService.authenticate(userRepository.save(user)), jwtResponse,
//                "Users should be same");
//    }

    @Test
    @DisplayName("Test AddUser - EmailNotUnique")
	void testAddUserEmailNotUnique() {
		doReturn(Optional.of(user)).when(userRepository).findByEmail(user.getEmail());

		assertThrows(EmailNotUniqueException.class, () ->
			authService.addUser(user)
		, "User Not Found, when it should be");
	}

    @Test
    @DisplayName("Test AddUser - PasswordNotValid")
    void testAddUserPasswordNotValid() {
        doReturn(false).when(passwordValidationUtil).passwordValidator(user.getPassword());

        assertThrows(PasswordNotValidException.class, () ->
                        authService.addUser(user)
                , "Valid Password, when it should not be");
    }

}
