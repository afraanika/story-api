package com.cefalo.storyapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getAllUsers() {
		Iterable<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}") 
	public ResponseEntity<Optional<User>> getUserById(@PathVariable int id) {
		Optional<User> user = userService.getUserById(id);
		if(user.isPresent()) return ResponseEntity.ok(user);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	public User getUserByEmail(@RequestParam(value="email")  String email) {
//		return userService.getUserByEmail(email);
//	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> addUser (@RequestBody User user) {
		try {
			return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{email}")
	public ResponseEntity<Optional<User>> updateUser(@PathVariable String email, @RequestBody User user) {
		Optional<User> userOptional = userService.updateUser(email, user);
		if (userOptional.isPresent()) return ResponseEntity.ok(userOptional);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		Optional<User> user = userService.deleteUser(id);
		if (user.isPresent()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
