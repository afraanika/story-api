package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository; 
	
	public User addUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> checkUser(User user) {
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		if (userOptional.isEmpty()) return Optional.empty();
		if(userOptional.get().getPassword().equals(user.getPassword())) return userOptional;
		return Optional.empty();
		
	}

}