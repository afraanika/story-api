package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.exceptions.PasswordNotValidException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.PasswordValidationUtil;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordValidationUtil passwordValidationUtil;
	
	public User addUser(User user) {	
		if(!passwordValidationUtil.passwordValidator(user.getPassword())) throw new PasswordNotValidException(
				"Password", "Not Valid. Please put valid password : must contain one capital letter, one small letter and one number");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public Optional<User> checkUser(User user) {
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		if (userOptional.isEmpty()) throw new EntityNotFoundException(User.class, "email", user.getEmail());
		if (passwordEncoder.matches(user.getPassword(), userOptional.get().getPassword())) return userOptional;
		return Optional.empty();
	}

}