package com.cefalo.storyapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.cefalo.storyapi.filters.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cefalo.storyapi.dto.StoryDTO;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.StoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class StoryControllerTest {


	@MockBean
	private StoryService storyService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private ObjectMapper mapper;
	
	private MockMvc mockMvc;


	private Story story;
	private StoryDTO storyDTO;
	
	@BeforeEach
	public void setup(){
	    mockMvc= MockMvcBuilders
	            .webAppContextSetup(webApplicationContext).addFilter(jwtFilter, "/*").build();
	    

	    User user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
		story = new Story(1, "Story Previous Tittle", "Story Previous Desciption", user);
		storyDTO =  new StoryDTO(1, "Story Tittle", "Story Desciption", "bill@gmail.com");
	}
	
	
	@Test
	@DisplayName("Get /stories  - Found")
	void testGetAllStories() throws Exception {
		
		doReturn(List.of(storyDTO))
			.when(storyService).getAllStories(1, 1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories?page=1&size=1"))
			
			.andExpect(status().isOk())
			
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].tittle", is("Story Tittle")))
			.andExpect(jsonPath("$[0].description",  is("Story Desciption")))
			.andExpect(jsonPath("$[0].authorName",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Get /stories  - Not Found")
	void testGetAllStoriesNotFound() throws Exception {
		
		doReturn(List.of())
			.when(storyService).getAllStories(1, 1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories?page=1&size=1"))
			.andExpect(status().isOk());
	}
	
	
	@Test
	@DisplayName("Get /stories/1  - Found")
	void testGetStoryById() throws Exception {
		doReturn(storyDTO).when(storyService).getStoryById(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories/{id}", 1))
			
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.tittle", is("Story Tittle")))
			.andExpect(jsonPath("$.description",  is("Story Desciption")))
			.andExpect(jsonPath("$.authorName",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Get /stories/1  - Not Found")
	void testGetStoryByIdNotFound() throws Exception {
		doThrow(new EntityNotFoundException(Story.class, "id", String.valueOf(1)))
			.when(storyService).getStoryById(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stories/{id}", 1))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Post /stories  - Success")
	void testAddStory() throws Exception {
		doReturn(storyDTO).when(storyService).addStory(story);
	
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stories")
		      .contentType(MediaType.APPLICATION_JSON)
    		  .characterEncoding("utf-8")
		      .content(asJsonString(story))
		      .accept(MediaType.APPLICATION_JSON))

		    .andExpect(status().isCreated())
		    
		    .andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.tittle", is("Story Tittle")))
			.andExpect(jsonPath("$.description",  is("Story Desciption")))
			.andExpect(jsonPath("$.authorName",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Put /stories/1  - Success")
	void testUpdateStory() throws Exception {
		doReturn(storyDTO).when(storyService).updateStory(story.getId(), story);
	
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stories/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8")
				.content(asJsonString(story))
				.accept(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk())
		    
		    .andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.tittle", is("Story Tittle")))
			.andExpect(jsonPath("$.description",  is("Story Desciption")))
			.andExpect(jsonPath("$.authorName",  is("bill@gmail.com")));
	}
	
	@Test
	@DisplayName("Put /stories/1  - Not Found")
	void testUpdateStoryNotFound() throws Exception {
		doThrow(new EntityNotFoundException(Story.class, "id", String.valueOf(1)))
			.when(storyService).updateStory(eq(story.getId()), any(Story.class));
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stories/{id}", 1)
			      .contentType(MediaType.APPLICATION_JSON)
	    		  .characterEncoding("utf-8")
			      .content(asJsonString(story)))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Delete /stories/1  - Success")
	void testDeleteStory() throws Exception {
		
		doNothing().when(storyService).deleteStory(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stories/{id}", 1))
			.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Delete /stories/1  - Not Found")
	void testDeleteStoryNotFound() throws Exception {
		
		doNothing().when(storyService).deleteStory(1);
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stories/{id}", 1))
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
