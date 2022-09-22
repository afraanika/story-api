package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isEmpty()) throw new EntityNotFoundException(User.class, "email", email);
		return (UserDetails) user.get();
	}

}
