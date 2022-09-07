package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {		
		return userRepository.save(user);			
	}

	public Optional<User> checkUser(User user) {
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		if (userOptional.isEmpty()) throw new EntityNotFoundException(User.class, "email", user.getEmail());
		if(userOptional.get().getPassword().equals(user.getPassword())) return userOptional;
		return Optional.empty();
	}

}