package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.UserConverterUtil;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CurrentUserService currentUserService;
	
	@Autowired
	private UserConverterUtil userConverterUtil;
	
	public Iterable<UserDTO> getAllUsers(int page, int size) {
		Iterable<User> allUsers = userRepository.findAll(PageRequest.of(page, size));
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
		isValidate(user.get().getId(), currentUserService.getUser().getId());
		setUser(user.get(), updatedUser);
		return userConverterUtil.entityToDTO(userRepository.save(user.get()));
	}

	public void deleteUser(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		isValidate(user.get().getId(), currentUserService.getUser().getId());
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