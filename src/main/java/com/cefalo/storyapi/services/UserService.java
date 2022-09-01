package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())	return user;
		return Optional.empty();
	}
	
	public Optional<User> updateUser(int id, User updatedUser) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) return Optional.empty();
		if (!updatedUser.getEmail().isBlank()) {
			user.get().setEmail(updatedUser.getEmail());
		}
		if (!updatedUser.getPassword().isBlank()) {
			user.get().setPassword(updatedUser.getPassword());
		}
		if (!updatedUser.getName().isBlank()) {
			user.get().setName(updatedUser.getName());
		}
		if (!updatedUser.getPassword().isBlank()) {
			user.get().setNumber(updatedUser.getNumber());
		}
		userRepository.save(user.get());
		return user;
	}

	public Optional<User> deleteUser(int id) {
		Optional<User> user = userRepository.findById(id);
		user.ifPresent(u -> userRepository.delete(u));
		return user;
	}
}