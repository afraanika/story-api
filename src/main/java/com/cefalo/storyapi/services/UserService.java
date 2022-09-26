package com.cefalo.storyapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EmailNotUniqueException;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<UserDTO> getAllUsers(int page, int size) {
		Page<User> allUsers = userRepository.findAll(PageRequest.of(page, size));
		return userConverterUtil.iterableUserDTO(allUsers.getContent());

	}
	
	public UserDTO getUserById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		return userConverterUtil.entityToDTO(user.get());
	}
	
	public UserDTO updateUser(Integer id, User updatedUser) {
		if(userRepository.findByEmail(updatedUser.getEmail()).isPresent() && 
				!(userRepository.findByEmail(updatedUser.getEmail()).get().getId().equals(id))) 
			throw new EmailNotUniqueException("Email", updatedUser.getEmail());
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));  
		isValid(user.get().getId(), currentUserService.getUser().getId());
		setUser(user.get(), updatedUser);
		return userConverterUtil.entityToDTO(userRepository.save(user.get()));
	}

	public void deleteUser(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		isValid(user.get().getId(), currentUserService.getUser().getId());
		userRepository.delete(user.get());
	} 
	
	private User setUser(User previousUser, User updatedUser) {
		previousUser.setEmail(updatedUser.getEmail());
		previousUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
		previousUser.setName(updatedUser.getName());
		previousUser.setNumber(updatedUser.getNumber());
		return previousUser;
	}

	protected boolean isValid(Integer userId, Integer currentUserId) {
		if(userId.equals(currentUserId)) return true;
		throw new AccessDeniedException(User.class);
	}
}