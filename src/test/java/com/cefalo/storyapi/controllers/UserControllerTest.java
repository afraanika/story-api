package com.cefalo.storyapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.filters.JwtFilter;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@MockBean
	private UserService userService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup(){
	    mockMvc= MockMvcBuilders
	            .webAppContextSetup(webApplicationContext).addFilter(jwtFilter, "/*").build();
	}
	
	@Test
	@DisplayName("Get /users/1  - Found")
	void testGetUser() throws Exception {}
	
	@Test
	@DisplayName("Get /users/1  - Found")
	void testGetUserById() throws Exception {
		UserDTO mockUser = new UserDTO(1, "Bill", "01236547893", "bill@gmail.com");
		doReturn(mockUser).when(userService).getUserById(1);
		
		mockMvc.perform(get("/api/v1/users/{id}", 1))
			
			.andExpect(status().isOk())
			
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Bill")))
			.andExpect(jsonPath("$.number",  is("01236547893")))
			.andExpect(jsonPath("$.email",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Get /users/1  - Not Found")
	void testGetUserByIdNotFound() throws Exception {
		doThrow(new EntityNotFoundException(User.class, "id", String.valueOf(1))).when(userService).getUserById(1);
		
		mockMvc.perform(get("/users/{id}", 1))
			.andExpect(status().isNotFound());
	}
	
//	@Test
//	@DisplayName("Put /users/1  - Success")
//	void testUpdateUser() throws Exception {
////		User user = new User(1, "Bill", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
////		UserDTO userDTO =  new UserDTO(1, "Billy", "01236547893", "bill@gmail.com");
////		doReturn(userDTO).when(userService).updateUser(1, user);
////		
////		mockMvc.perform(put("/users/{id}", 1)
////				.contentType(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(user)))
////			
////			.andExpect(status().isCreated())
////			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//	
//		mockMvc.perform( MockMvcRequestBuilders
//		      .put("/employees/{id}", 1)
//		      .content(asJsonString(new User(1, "Bill", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO")))
//		      .contentType(MediaType.APPLICATION_JSON)
//		      .accept(MediaType.APPLICATION_JSON))
//		      .andExpect(status().isOk())
//			
//			.andExpect(jsonPath("$.id", is(1)))
//			.andExpect(jsonPath("$.name", is("Billy")))
//			.andExpect(jsonPath("$.number",  is("01236547893")))
//			.andExpect(jsonPath("$.email",  is("bill@gmail.com")));
//		
//	}
	
	@Test
	@DisplayName("Delete /users/1  - Success")
	void testDeleteUser() throws Exception {
		
		doNothing().when(userService).deleteUser(4);
		
		mockMvc.perform(delete("/users/{id}", 4))
			.andExpect(status().isNoContent());
	}
	
	static String asJsonString(final Object object){
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
