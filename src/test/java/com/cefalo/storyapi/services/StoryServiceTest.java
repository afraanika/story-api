package com.cefalo.storyapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
public class StoryServiceTest {

	@MockBean
	private StoryRepository storyRepository;

	@Autowired
	private StoryConverterUtil storyConverterUtil;

	@Autowired
	private StoryService storyService;

	@MockBean
	private CurrentUserService currentUserService;

	private Story story;

	private User user;

	@BeforeEach
	public void setup(){
	    user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
		story = new Story(1, "Story Previous Tittle", "Story Previous Desciption", user);
	}

//	@Test
//	@DisplayName("Test getAllStories - Success")
//	void shouldReturnUserList( ){
//		Page<Story> pagedTasks = new PageImpl(Arrays.asList(story, story));
//		Mockito.when(this.storyRepository.findAll((Pageable) pagedTasks)).thenReturn(pagedTasks);
//
//		List<StoryDTO> stories = storyService.getAllStories(0,2);
//
//		assertEquals(2, stories.size(), "findAll should return 2 users");
//	}

	@Test
	@DisplayName("Test getStoryById - Found")
	void shouldReturnStoryById() {
		doReturn(Optional.of(story)).when(storyRepository).findById(story.getId());

		StoryDTO storyDTO = storyConverterUtil.entityToDTO(story);
		StoryDTO returnedStoryDTO = storyService.getStoryById(1);

		assertTrue(Optional.of(returnedStoryDTO).isPresent(), "Story Not Found");
		assertEquals(returnedStoryDTO, storyDTO, "Stories should be same");
	}

	@Test
	@DisplayName("Test getStoryById - Not Found")
	void shouldThrowEntityNotFoundException() {
		doReturn(Optional.empty()).when(storyRepository).findById(1);

		assertThrows(EntityNotFoundException.class, () ->
						storyService.getStoryById(story.getId())
				, "Found Story, when it should not be");
	}

	@Test
	@DisplayName("Test UpdateUser - Not Found")
	void shouldThrowEntityNotFoundExceptionWhileUpdating() {
		doReturn(Optional.empty()).when(storyRepository).findById(story.getId());

		assertThrows(EntityNotFoundException.class, () ->
						storyService.updateStory(story.getId(), story)
				, "Story Found, when it should not be");
	}

	@Test
	@DisplayName("Test addStory")
	void testAddStory() {
		User user = new User(1, "Billy", "01236547893", "bill@gmail.com", "A2Sa3A1ABSRO");
		Story givenStory = new Story("Story Previous Tittle", "Story Previous Desciption");

		doReturn(story).when(storyRepository).save(givenStory);
		doReturn(user).when(currentUserService).getUser();

		Story returnedStory = storyRepository.save(givenStory);

		Assertions.assertNotNull(returnedStory, "Saved Story should not be null");
		Assertions.assertEquals(1, returnedStory.getId(), "ID of the Story should be 1");
	}
	@Test
	@DisplayName("Test setStoryProperties - Success")
	void shouldReturnUpdatedStory() {
		Story givenStory = new Story(1, "Story Updated Tittle", "Story Updated Desciption", user);
		Story updatedStory = storyService.setStoryProperties(story, givenStory);

		assertEquals(givenStory, updatedStory, "Users should be equal");
	}

	@Test
	@DisplayName("Test isValid - Success")
	void shouldReturnTrue() {
		Story givenStory = new Story(1, "Story Updated Tittle", "Story Updated Desciption", user);
		boolean isValid = storyService.isValid(story.getUser().getId(), givenStory.getUser().getId());

		assertTrue(isValid);
	}

	@Test
	@DisplayName("Test isValid - AccessDenied")
	void shouldThrowAccessDeniedException() {
		assertThrows(AccessDeniedException.class,
				() -> storyService.isValid(story.getUser().getId(), 2),
				"Users should not be same");
	}

}
