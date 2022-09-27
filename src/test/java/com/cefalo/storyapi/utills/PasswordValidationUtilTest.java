package com.cefalo.storyapi.utills;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.utils.PasswordValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class PasswordValidationUtilTest {

    @Autowired
    private PasswordValidationUtil passwordValidationUtil;
    private User user;

    @BeforeEach
    public void setup(){
        user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sas3A1ABSRO");
    }

    @Test
    @DisplayName("Test PasswordValidator - Success")
    void shouldReturnTrue(){
        boolean isPasswordValid = passwordValidationUtil.passwordValidator(user.getPassword());

        assertTrue(isPasswordValid, "Password Not Valid");

    }
}
