package com.cefalo.storyapi.utils;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidationUtil {

    @Autowired
    private UserRepository userRepository;

    public boolean emailValidator(Integer id, User updatedUser) {
        if (userRepository.findByEmail(updatedUser.getEmail()).isPresent() &&
                !(userRepository.findByEmail(updatedUser.getEmail()).get().getId().equals(id))) return true;
        return false;
    }
}
