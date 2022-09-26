package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;
import com.cefalo.storyapi.utils.IAuthenticationFacade;

@Service
public class CurrentUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	public User getUser() {
		String email = getCurrentUserEmail();
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty()) throw new EntityNotFoundException(User.class, "email", email);
		return user.get();
	}
	
	private String getCurrentUserEmail() {
		if (!authenticationFacade.getAuthentication().isAuthenticated()) throw new AccessDeniedException(User.class);
		Authentication authentication = authenticationFacade.getAuthentication();
		return authentication.getName();
	}
}
