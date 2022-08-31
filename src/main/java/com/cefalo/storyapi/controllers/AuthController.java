package com.cefalo.storyapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<? extends Object> addUser (@RequestBody User user) {
		try {
			return new ResponseEntity<>(authService.addUser(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage().toString());
		}
	} 
	
	@RequestMapping(method = RequestMethod.POST, value = "/signin")
	public ResponseEntity<? extends Object> checkUser(@RequestBody User user) {
		Optional<User> userOptional = authService.checkUser(user);
		if(userOptional.isPresent()) return ResponseEntity.ok(userOptional);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}