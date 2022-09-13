package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.UserConverterUtil;

import dto.UserDTO;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CurrentUserService currentUserService;
	
	@Autowired
	private UserConverterUtil userConverterUtil;
	
	public Iterable<UserDTO> getAllUsers() {
		Iterable<User> allUsers = userRepository.findAll();
		return userConverterUtil.iterableUserDTO(allUsers);
		
	}
	
	public UserDTO getUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		return userConverterUtil.entityToDTO(user.get());
	}
	
	public UserDTO updateUser(Integer id, User updatedUser) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));  
		isValidate(user.get());
		setUser(user.get(), updatedUser);
		return userConverterUtil.entityToDTO(userRepository.save(user.get()));
	}

	public void deleteUser(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		isValidate(user.get());
		userRepository.delete(user.get());
	} 
	
	private User setUser(User previousUser, User updatedUser) {
		previousUser.setEmail(updatedUser.getEmail());
		previousUser.setPassword(updatedUser.getPassword());
		previousUser.setName(updatedUser.getName());
		previousUser.setNumber(updatedUser.getNumber());
		return previousUser;
	}

	private boolean isValidate(Integer userId, Integer currentUserId) {
		if(userId.equals(currentUserId)) return true;
		throw new AccessDeniedException(User.class);
	}
}