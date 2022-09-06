package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		return user.get();
	}
	
	public User updateUser(int id, User updatedUser) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));  
		setUser(user.get(), updatedUser);
		userRepository.save(user.get());
		return user.get();
	}

	public User deleteUser(int id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		userRepository.delete(user.get());
		return user.get();
	}
	
	private User setUser(User previousUser, User updatedUser) {
		previousUser.setEmail(updatedUser.getEmail());
		previousUser.setPassword(updatedUser.getPassword());
		previousUser.setName(updatedUser.getName());
		previousUser.setNumber(updatedUser.getNumber());
		return previousUser;
	}                             
}