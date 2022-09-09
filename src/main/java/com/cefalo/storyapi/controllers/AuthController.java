package com.cefalo.storyapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.AuthService;
import com.cefalo.storyapi.services.JwtService;

@RestController
public class AuthController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<? extends Object> addUser (@RequestBody User user) {
		User createdUser = authService.addUser(user);
		JwtResponse jwtToken = jwtService.authenticate(createdUser);
		return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
	} 
	
	@RequestMapping(method = RequestMethod.POST, value = "/signin")
	public ResponseEntity<? extends Object> checkUser(@RequestBody User user) {
		Optional<User> userOptional = authService.checkUser(user);
		if(userOptional.isPresent()) {
			JwtResponse jwtToken = jwtService.authenticate(userOptional.get());
			return ResponseEntity.ok(jwtToken);
		}
		return new ResponseEntity<>("Incorrect Email or Password", HttpStatus.UNAUTHORIZED);
	}

}