package com.cefalo.storyapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.utils.StoryConverterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cefalo.storyapi.dto.StoryDTO;
import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.StoryRepository;

@SpringBootTest
public class StoryServiceTest {

	@MockBean
	private StoryRepository storyRepository;

	@Autowired
	private StoryConverterUtil storyConverterUtil;

	@Autowired
	private StoryService storyService;

	private Story story;

	@BeforeEach
	public void setup(){
	    User user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
		story = new Story(1, "Story Previous Tittle", "Story Previous Desciption", user);
	}

	@Test
	@DisplayName("Test GetAllStories ")
	void testGetAllStories( ){
		doReturn(Arrays.asList(story, story)).when(storyRepository).findAll();

		List<StoryDTO> stories = storyService.getAllStories(1, 2);

		Assertions.assertEquals(2, stories.size(), "findAll should return 2 users");
	}

	@Test
	@DisplayName("Test GetStoryById - Found")
	void testGetStoryByIdFound() {
		doReturn(Optional.of(story)).when(storyRepository).findById(story.getId());

		StoryDTO storyDTO = storyConverterUtil.entityToDTO(story);
		StoryDTO returnedStoryDTO = storyService.getStoryById(1);

		assertTrue(Optional.of(returnedStoryDTO).isPresent(), "Story Not Found");
		assertEquals(returnedStoryDTO, storyDTO, "Stories should be same");
	}

	@Test
	@DisplayName("Test GetStoryById - Not Found")
	void testGetStoryByIdNotFound() {
		doReturn(Optional.empty()).when(storyRepository).findById(1);

		assertThrows(EntityNotFoundException.class, () ->
						storyService.getStoryById(story.getId())
				, "Found Story, when it should not be");
	}

//	@Test
//	@DisplayName("Test addStory")
//	void testAddStory() {
//		doReturn(story).when(storyRepository).save(story);
//
//		Story returnedStory = storyRepository.save(story);
//
//		Assertions.assertNotNull(returnedStory, "Saved Story should not be null");
//		Assertions.assertEquals(1, returnedStory.getId(), "ID of the Story should be 1");
//	}
}
