package com.cefalo.storyapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.utils.JwtUtil;

@Service
public class JwtService {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	public JwtResponse authenticate(User user) throws Exception {
		
		final User user1 = (User) userService.loadUserByUsername(user.getEmail());
		final String token = jwtUtil.generateToken(user1);
		
		return new JwtResponse(token);
	}
}
