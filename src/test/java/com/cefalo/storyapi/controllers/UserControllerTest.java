package com.cefalo.storyapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.cefalo.storyapi.filters.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@MockBean
	private UserService userService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ObjectMapper mapper;
	
	private MockMvc mockMvc;
	
	private User user;
	
	private UserDTO userDTO;
	
	@BeforeEach
	public void setup(){
	    mockMvc= MockMvcBuilders
	            .webAppContextSetup(webApplicationContext).addFilter(jwtFilter, "/*").build();
	    

		user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
		userDTO =  new UserDTO(1, "Billy", "01236547893", "bill@gmail.com");
	}
	
	@Test
	@DisplayName("Get /users  - Found")
	void testGetAllUsers() throws Exception {
		
		doReturn(List.of(userDTO))
			.when(userService).getAllUsers();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?page=1&size=1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Billy")))
			.andExpect(jsonPath("$[0].number",  is("01236547893")))
			.andExpect(jsonPath("$[0].email",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Get /users  - Not Found")
	void testGetAllUsersNotFound() throws Exception {
		
		doReturn(List.of())
			.when(userService).getAllUsers();
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?page=1&size=1"))
			.andExpect(status().isOk());
	}
	
	
	@Test
	@DisplayName("Get /users/1  - Found")
	void testGetUserById() throws Exception {
		doReturn(userDTO).when(userService).getUserById(1);
		
		mockMvc.perform(get("/api/v1/users/{id}", 1))
			
			.andExpect(status().isOk())
			
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Billy")))
			.andExpect(jsonPath("$.number",  is("01236547893")))
			.andExpect(jsonPath("$.email",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Get /users/1  - Not Found")
	void testGetUserByIdNotFound() throws Exception {
		doThrow(new EntityNotFoundException(User.class, "id", String.valueOf(1))).when(userService).getUserById(1);
		
		mockMvc.perform(get("/api/v1/users/{id}", 1))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Put /users/1  - Success")
	void testUpdateUser() throws Exception {
		doReturn(userDTO).when(userService).updateUser(user.getId(), user);
	
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", 1)
		      .contentType(MediaType.APPLICATION_JSON)
    		  .characterEncoding("utf-8")
		      .content(asJsonString(user))
		      .accept(MediaType.APPLICATION_JSON))

		    .andExpect(status().isOk())
		    
		    .andExpect(jsonPath("$.id", is(1)))
		    .andExpect(jsonPath("$.name", is("Billy")))
		    .andExpect(jsonPath("$.number",  is("01236547893")))
			.andExpect(jsonPath("$.email",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Put /users/1  - Not Found")
	void testUpdateUserNotFound() throws Exception {
		doThrow(new EntityNotFoundException(User.class, "id", String.valueOf(1)))
			.when(userService).updateUser(user.getId(), user);
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}", 1)
			      .contentType(MediaType.APPLICATION_JSON)
	    		  .characterEncoding("utf-8")
			      .content(asJsonString(user)))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Delete /users/1  - Success")
	void testDeleteUser() throws Exception {
		doNothing().when(userService).deleteUser(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}", 1))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Delete /users/1  - Not Found")
	void testDeleteUserNotFound() throws Exception {
		doNothing().when(userService).deleteUser(1);
		
		mockMvc.perform(delete("/api/v1/users/{id}", 1))
			.andExpect(status().isNoContent());
	}
	
	private String asJsonString(final Object object){
		try {
			return mapper.writeValueAsString(object); 
		} catch (JsonProcessingException  e) {
			throw new RuntimeException("Failed to map obj " + object, e);
		}
	}
}
