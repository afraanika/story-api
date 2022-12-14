package com.cefalo.storyapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.UserService;

@RestController
@RequestMapping(path = "${apiPrefix}/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
		Iterable<UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}") 
	public ResponseEntity<? extends Object> getUserById(@PathVariable Integer id) {
		UserDTO user = userService.getUserById(id);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(user);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<? extends Object> updateUser(@PathVariable Integer id, @RequestBody User user) {
		UserDTO updatedUser = userService.updateUser(id, user);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser );
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<? extends Object> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}