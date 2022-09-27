package com.cefalo.storyapi.services;

import java.util.List;
import java.util.Optional;

import com.cefalo.storyapi.utils.UniqueEmailValidationUtil;
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

	@Autowired
	private UniqueEmailValidationUtil uniqueEmailValidationUtil;
	
	public List<UserDTO> getAllUsers() {
		List<User> allUsers = userRepository.findAll();
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
		if(uniqueEmailValidationUtil.emailValidator(id, updatedUser))
			throw new EmailNotUniqueException("Email", updatedUser.getEmail());
		isValid(user.get().getId(), currentUserService.getUser().getId());
		setUserProperties(user.get(), updatedUser);
		return userConverterUtil.entityToDTO(userRepository.save(user.get()));
	}

	public boolean deleteUser(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "id", String.valueOf(id));
		isValid(user.get().getId(), currentUserService.getUser().getId());
		userRepository.delete(user.get());
		return true;
	} 
	
	protected User setUserProperties(User previousUser, User updatedUser) {
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