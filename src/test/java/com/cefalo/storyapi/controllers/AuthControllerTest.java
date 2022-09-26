package com.cefalo.storyapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cefalo.storyapi.filters.JwtFilter;
import com.cefalo.storyapi.utils.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cefalo.storyapi.models.JwtResponse;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.AuthService;
import com.cefalo.storyapi.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@MockBean
	private AuthService authService;

	private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper mapper;

    private User user;

    private String token;

    private JwtResponse jwtResponse;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        user =  new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaWxsQGdtYWlsLmNvbSIsImlhdCI6MTY2MzgyNjY3OCwiZXhwIjoxNjYzODYyNjc4fQ.i7aH_-_HICz7TWeVobrv18RvazEmbyLH8QTKXjG8kAQ";
        jwtResponse = new JwtResponse(token);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken
				(user, null, new ArrayList<>()));
    }

	@Test
	@DisplayName("Post /signup - Success")
	void testAddUser() throws Exception {
		doReturn(jwtResponse).when(authService).addUser(user);

        this.mockMvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))

			.andDo(print())
		    .andExpect(status().isCreated())

		    .andExpect(jsonPath("$.jwtTokenString", is(token)));
	}

	@Test
	@DisplayName("Post /signin - Success")
	void testValidateUser() throws Exception {
		doReturn(jwtResponse).when(authService).validateUser(user);

		this.mockMvc.perform(post("/api/v1/signin")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("utf-8")
						.content(asJsonString(user))
						.accept(MediaType.APPLICATION_JSON))

				.andDo(print())
				.andExpect(status().isOk())

				.andExpect(jsonPath("$.jwtTokenString", is(token)));
	}

	private String asJsonString(final User object){
		System.out.println(object.getEmail());
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException  e) {
			throw new RuntimeException("Failed to map obj " + object, e);
		}
	}
}
