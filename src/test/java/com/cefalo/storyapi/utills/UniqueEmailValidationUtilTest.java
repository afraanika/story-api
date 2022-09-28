package com.cefalo.storyapi.utills;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.utils.UniqueEmailValidationUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UniqueEmailValidationUtilTest {
    @Autowired
    private UniqueEmailValidationUtil uniqueEmailValidationUtil;

    @Test
    @DisplayName("Test emailValidator - True")
    void shouldReturnTrue() {
        Optional<User> existingUser = Optional.of(new User(1, "Billy",
                "01236547893", "billy@gmail.com", "A2Sas3A1ABSRO"));

        boolean isEmailUnique = uniqueEmailValidationUtil.emailValidator(1, existingUser);
        assertFalse(isEmailUnique);
    }

    @Test
    @DisplayName("Test emailValidator - False")
    void shouldReturnFalse() {
        Optional<User> existingUser = Optional.of(new User(1, "Billy",
                "01236547893", "billy@gmail.com", "A2Sas3A1ABSRO"));

        boolean isEmailUnique = uniqueEmailValidationUtil.emailValidator(2, existingUser);
        assertTrue(isEmailUnique);
    }
}
