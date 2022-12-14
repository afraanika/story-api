package com.cefalo.storyapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "${apiPrefix}")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<? extends Object> addUser (@RequestBody User user) {
		JwtResponse jwtToken = authService.addUser(user);
		return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
	} 
	
	@RequestMapping(method = RequestMethod.POST, value = "/signin")
	public ResponseEntity<? extends Object> validateUser(@RequestBody User user) {
		JwtResponse jwtToken = authService.validateUser(user);
		return ResponseEntity.ok(jwtToken);
	}

}