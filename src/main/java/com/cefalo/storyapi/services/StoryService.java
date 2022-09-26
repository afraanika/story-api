package com.cefalo.storyapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.dto.StoryDTO;
import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.StoryRepository;
import com.cefalo.storyapi.utils.StoryConverterUtil;

@Service
public class StoryService {
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private CurrentUserService currentUserService;
	
	@Autowired
	private StoryConverterUtil storyConverterUtil;

	public List<StoryDTO> getAllStories(int page, int size) {
		Page<Story> stories = storyRepository.findAll(PageRequest.of(page, size));
		return storyConverterUtil.iterableStoryDTO(stories.getContent());
	}

	public StoryDTO getStoryById(Integer id) {
		Optional<Story> story = storyRepository.findById(id);
		if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
		return storyConverterUtil.entityToDTO(story.get());
	}

	public StoryDTO addStory(Story story) {
		story.setUser(currentUserService.getUser());
		return storyConverterUtil.entityToDTO(storyRepository.save(story));
	}
	
	public StoryDTO updateStory(Integer id, Story updatedStory) {
		Optional<Story> story = storyRepository.findById(id);
		if(story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
		isValid(story.get().getId(), currentUserService.getUser().getId());
		setStory(story.get(), updatedStory);
		return storyConverterUtil.entityToDTO(storyRepository.save(story.get()));
	}

	public void deleteStory(Integer id) {
		Optional<Story> story = storyRepository.findById(id);
		if(story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
		isValid(story.get().getId(), currentUserService.getUser().getId());
		storyRepository.delete(story.get());
	}
	
	private Story setStory(Story previousStory, Story updatedStory) {
		previousStory.setTittle(updatedStory.getTittle());
		previousStory.setDescription(updatedStory.getDescription());
		return previousStory;
	}   

	private boolean isValid(Integer userId, Integer currentUserId) {
		if(userId.equals(currentUserId))	return true;
		throw new AccessDeniedException(User.class);
	}
}
