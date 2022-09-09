package com.cefalo.storyapi.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.services.StoryService;

import dto.StoryDTO;

@RestController
@RequestMapping(path = "${apiPrefix}/stories")
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<StoryDTO>> getAllStories() {
		Iterable<StoryDTO> stories = storyService.getAllStories();
		return ResponseEntity.ok(stories);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}") 
	public ResponseEntity<? extends Object> getStoryById(@PathVariable Integer id) {
		StoryDTO story = storyService.getStoryById(id);
		return ResponseEntity.ok(story);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<? extends Object> addStory (@RequestBody Story story) {
		StoryDTO createdStory = storyService.addStory(story);
		return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<? extends Object> updateStory(@PathVariable Integer id, @RequestBody Story story) {
		StoryDTO updatedStory = storyService.updateStory(id, story);
		return ResponseEntity.ok(updatedStory);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<? extends Object> deleteStory(@PathVariable Integer id) {
		storyService.deleteStory(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
