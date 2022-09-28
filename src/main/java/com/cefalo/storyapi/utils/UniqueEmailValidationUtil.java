package com.cefalo.storyapi.utils;

import com.cefalo.storyapi.models.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailValidationUtil {

    public boolean emailValidator(Integer id, Optional<User> existingUser) {
        return existingUser.isPresent() &&
                !(existingUser.get().getId().equals(id));
    }
}
