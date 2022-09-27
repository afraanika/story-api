package com.cefalo.storyapi.services;

import java.util.Optional;

import com.cefalo.storyapi.exceptions.IncorrectEmailOrPasswordException;
import com.cefalo.storyapi.models.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.EmailNotUniqueException;
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
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PasswordValidationUtil passwordValidationUtil;
	
	public JwtResponse addUser(User user) {
		if(userRepository.findByEmail(user.getEmail()).isPresent()) throw new EmailNotUniqueException("Email", user.getEmail());
		if(!passwordValidationUtil.passwordValidator(user.getPassword())) throw new PasswordNotValidException();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User createdUser = userRepository.save(user);
		return jwtService.authenticate(createdUser);
	}

	public JwtResponse validateUser(User user) {
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		if (userOptional.isEmpty()) throw new EntityNotFoundException(User.class, "email", user.getEmail());
		if (!passwordEncoder.matches(user.getPassword(), userOptional.get().getPassword())) throw new IncorrectEmailOrPasswordException();
		return jwtService.authenticate(userOptional.get());
	}

}