package com.cefalo.storyapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	@MockBean
	private UserDetailsServiceImp userDetailsServiceImp;

	@Autowired
	private ObjectMapper mapper;

	private User user;

	@BeforeEach
	public void setup(){
		user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
}

	@Test
	@DisplayName("Test GetAllUsers - Success")
	void testGetAllUsers( ){
		doReturn(Arrays.asList(user, user)).when(userRepository).findAll();

		List<UserDTO> users = userService.getAllUsers(1, 2);

		Assertions.assertEquals(2, users.size(), "findAll should return 2 users");
	}

	@Test
	@DisplayName("Test GetUserById - Found")
	void testGetUserByIdFound() {
		doReturn(Optional.of(user)).when(userRepository).findById(user.getId());

		UserDTO userDTO = userConverterUtil.entityToDTO(user);
		UserDTO returnedUserDTO = userService.getUserById(user.getId());

		assertTrue(Optional.of(returnedUserDTO).isPresent(), "User Not Found");
		assertEquals(returnedUserDTO, userDTO, "Users should be same");
	}

	@Test
	@DisplayName("Test GetUserById - Not Found")
	void testGetUserByIdNotFound() {
		doReturn(Optional.empty()).when(userRepository).findById(1);

		assertThrows(EntityNotFoundException.class, () ->
			userService.getUserById(user.getId())
		, "User Found, when it should not be");
	}

//	@Test
//	@DisplayName("Test UpdateUser - Successful")
//	void testUpdateUser() {
//		String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaWxsQGdtYWlsLmNvbSIsImlhdCI6MTY2MzgyNjY3OCwiZXhwIjoxNjYzODYyNjc4fQ.i7aH_-_HICz7TWeVobrv18RvazEmbyLH8QTKXjG8kAQ";
//		RequestBuilder request = MockMvcRequestBuilders
//				.post("/api/adverts/category")
//				.content(asJsonString(user))
//				.header("Authorization", "Bearer " + jwtToken)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.accept(MediaType.APPLICATION_JSON);
//
//		doReturn(user).when(userRepository).save(user);
//
//		when(userDetailsServiceImp.loadUserByUsername(user.getUsername())).thenReturn(user);
//		UserDTO returnedUser = userService.updateUser(user.getId(), user);
//
//		ConsoleIOContext.AllSuggestionsCompletionTask mockMvc;
//		MvcResult mvcResult = mockMvc.perform(request)
//				.andExpect(status().is2xxSuccessful());
//
//	}

	@Test
	@DisplayName("Test UpdateUser - Not Found")
	void testUpdateUserNotFound() {
		doReturn(Optional.empty()).when(userRepository).findById(user.getId());

		assertThrows(EntityNotFoundException.class, () ->
				userService.updateUser(user.getId(), user)
		, "User Found, when it should not be");
	}

	private String asJsonString(final Object object){
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to map obj " + object, e);
		}
	}
}
