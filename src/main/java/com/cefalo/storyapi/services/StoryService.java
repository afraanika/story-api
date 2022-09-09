package com.cefalo.storyapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.repositories.StoryRepository;

@Service
public class StoryService {
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private CurrentUserService currentUserService;
	
	public Iterable<Story> getAllStories() {
		return storyRepository.findAll();
	}
	
	public Story getStoryById(int id) {
		Optional<Story> story = storyRepository.findById(id);
		if (story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
		return story.get();
	}
	
	public Story addStory(Story story) {
		story.setUser(currentUserService.getUser());
		return storyRepository.save(story);			
	}
	
	public Story updateStory(int id, Story updatedStory) {
		Optional<Story> story = storyRepository.findById(id);
		if(story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id)); 
		userAccessValidation(story.get());
		setStory(story.get(), updatedStory);
		storyRepository.save(story.get());
		return story.get();
	}

	public Story deleteStory(int id) {
		Optional<Story> story = storyRepository.findById(id);
		if(story.isEmpty()) throw new EntityNotFoundException(Story.class, "id", String.valueOf(id));
		userAccessValidation(story.get());
		storyRepository.delete(story.get());
		return story.get();
	}
	
	private Story setStory(Story previousStory, Story updatedStory) {
		previousStory.setTittle(updatedStory.getTittle());
		previousStory.setDescription(updatedStory.getDescription());
		previousStory.setUser(updatedStory.getUser());
		return previousStory;
	}   

	private void userAccessValidation(Story story) {
		if(!(story.getUser().getId() == currentUserService.getUser().getId()))
			throw new AccessDeniedException(User.class, story.getTittle());
	}
}
