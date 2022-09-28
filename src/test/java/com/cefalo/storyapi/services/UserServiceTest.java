package com.cefalo.storyapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.utils.UserConverterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserConverterUtil userConverterUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	private ObjectMapper mapper;

	private User user;

	@BeforeEach
	public void setUp(){
		user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
	}

	@Test
	@DisplayName("Test GetAllUsers - Success")
	void shouldReturnUserList( ){
		doReturn(Arrays.asList(user, user)).when(userRepository).findAll();

		List<UserDTO> users = userService.getAllUsers();

		Assertions.assertEquals(2, users.size(), "findAll should return 2 users");
	}

	@Test
	@DisplayName("Test GetUserById - Found")
	void shouldReturnUserWithGivenId() {
		doReturn(Optional.of(user)).when(userRepository).findById(user.getId());

		UserDTO userDTO = userConverterUtil.entityToDTO(user);
		UserDTO returnedUserDTO = userService.getUserById(user.getId());

		assertTrue(Optional.of(returnedUserDTO).isPresent(), "User Not Found");
		assertEquals(returnedUserDTO, userDTO, "Users should be same");
	}

	@Test
	@DisplayName("Test GetUserById - Not Found")
	void shouldThrowEntityNotFoundException() {
		doReturn(Optional.empty()).when(userRepository).findById(1);

		assertThrows(EntityNotFoundException.class, () ->
			userService.getUserById(user.getId())
		, "User Found, when it should not be");
	}

//	@Test
//	@DisplayName("Test UpdateUser - Successful")
//	void shouldReturnUpdatedUserDTO() {
//		User updatedUser = new User(1, "Billy", "01236547893", "billy@gmail.com", "A2Sa3A1ABSRO");
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
//
//		doReturn(Optional.of(updatedUser)).when(userRepository).findById(user.getId());
//
//		UserDTO userDTO = userConverterUtil.entityToDTO(updatedUser);
//		UserDTO returnedUserDTO = userService.updateUser(user.getId(), user);
//
//		assertEquals(userDTO, returnedUserDTO, "Users should be equal");
//	}

	@Test
	@DisplayName("Test UpdateUser - Not Found")
	void shouldThrowEntityNotFoundExceptionWhileUpdating() {
		doReturn(Optional.empty()).when(userRepository).findById(user.getId());

		assertThrows(EntityNotFoundException.class, () ->
				userService.updateUser(user.getId(), user)
		, "User Found, when it should not be");
	}

//	@Test
//	@DisplayName("Test DeleteUser - Success")
//	void shouldReturnTrue() {
//		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
//
//		doReturn(Optional.of(user)).when(userRepository).findById(user.getId());
//
//		boolean deletedSuccessfully = userService.deleteUser(user.getId());
//
//		assertTrue(deletedSuccessfully);
//	}
	@Test
	@DisplayName("Test DeleteUser - Not Found")
	void shouldThrowEntityNotFoundExceptionWhileDeleting() {
		doReturn(Optional.empty()).when(userRepository).findById(user.getId());

		assertThrows(EntityNotFoundException.class, () ->
						userService.deleteUser(user.getId())
				, "User Found, when it should not be");
	}

	@Test
	@DisplayName("Test setUserProperties - Success")
	void shouldReturnUpdatedUser() {
		User givenUser = new User(1, "Billy", "01236547893", "billy@gmail.com", "A2Sa3A1ABSRO");
		User updatedUser = userService.setUserProperties(user, givenUser);

		assertEquals(givenUser, updatedUser, "Users should be equal");
	}

	@Test
	@DisplayName("Test isValid - Success")
	void shouldReturnTrue() {
		User givenUser = new User(1, "Billy", "01236547893", "billy@gmail.com", "A2Sa3A1ABSRO");
		boolean isValid = userService.isValid(user.getId(), givenUser.getId());

		assertTrue(isValid);
	}

	@Test
	@DisplayName("Test isValid - AccessDenied")
	void shouldThrowAccessDeniedException() {
		assertThrows(AccessDeniedException.class,
				() -> userService.isValid(user.getId(), 2),
				"Users should not be same");
	}
//
//	private String asJsonString(final Object object){
//		try {
//			return mapper.writeValueAsString(object);
//		} catch (JsonProcessingException e) {
//			throw new RuntimeException("Failed to map obj " + object, e);
//		}
//	}
}
