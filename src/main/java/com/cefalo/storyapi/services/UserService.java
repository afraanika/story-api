package com.cefalo.storyapi.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
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
		return user;
	}
	
	public Optional<User> getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isEmpty()) { return Optional.empty(); }
		return user;
	}
	
	public Optional<User> addUser(User user) {
		userRepository.save(user);
		return userRepository.findByEmail(user.getEmail());
	}
	
	public Optional<User> updateUser(String email, User updatedUser) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isEmpty()) return Optional.empty();
		user.ifPresent(u -> { 
			u.setEmail(updatedUser.getEmail());
			u.setPassword(updatedUser.getPassword());
			});
		userRepository.save(user.get());
		return user;
	}

	public Optional<User> deleteUser(int id) {
		Optional<User> user = userRepository.findById(id);
		user.ifPresent(u -> userRepository.delete(u));
		return user;
	}
}
