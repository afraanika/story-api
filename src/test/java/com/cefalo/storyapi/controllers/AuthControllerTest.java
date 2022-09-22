//package com.cefalo.storyapi.controllers;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.cefalo.storyapi.models.JwtResponse;
//import com.cefalo.storyapi.models.User;
//import com.cefalo.storyapi.services.AuthService;
//import com.cefalo.storyapi.services.UserService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class AuthControllerTest {
//	
//	@MockBean
//	private AuthService authService;
//	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@Autowired
//	private ObjectMapper mapper;
//	
//	@Test
//	@DisplayName("Post /users - Success") 
//	void testAddUser() throws Exception {
//		User user =  new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
//		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaWxsQGdtYWlsLmNvbSIsImlhdCI6MTY2MzgyNjY3OCwiZXhwIjoxNjYzODYyNjc4fQ.i7aH_-_HICz7TWeVobrv18RvazEmbyLH8QTKXjG8kAQ";
//		JwtResponse jwtResponse = new JwtResponse(token);
//		
//		System.out.println(user.getEmail());
//		doReturn(jwtResponse).when(authService).addUser(user);
//	
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
//		      .contentType(MediaType.APPLICATION_JSON)
//		      .content(asJsonString(user)))
//		
//			.andDo(print())
//		    .andExpect(status().isCreated())
//		    
//		    .andExpect(jsonPath("$.jwtResponse", is(jwtResponse)));
//	}
//	
//	private String asJsonString(final User object){
//		System.out.println(object.getEmail());
//		try {
//			return mapper.writeValueAsString(object);
//		} catch (JsonProcessingException  e) {
//			throw new RuntimeException("Failed to map obj " + object, e);
//		}
//	}
//}
