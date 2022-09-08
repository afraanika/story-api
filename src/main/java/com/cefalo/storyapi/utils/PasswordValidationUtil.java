package com.cefalo.storyapi.utils;

import java.util.regex.*;

import org.springframework.stereotype.Component;    

@Component
public class PasswordValidationUtil {

	public boolean passwordValidator(String password) {
		return Pattern.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9@$!%*?#&]{8,}", password); 
	}
}
