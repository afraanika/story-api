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

@RestController
@RequestMapping("/stories")
public class StoryController {
	
	@Autowired
	private StoryService storyService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Iterable<Story>> getAllStories() {
		Iterable<Story> stories = storyService.getAllStories();
		return ResponseEntity.ok(stories);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<? extends Object> addStory (@RequestBody Story story) {
		Story createdStory = storyService.addStory(story);
		return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}") 
	public ResponseEntity<? extends Object> getStoryById(@PathVariable int id) {
		Story story = storyService.getStoryById(id);
		return ResponseEntity.ok(story);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<? extends Object> updateStory(@PathVariable int id, @RequestBody Story story) {
		Story updatedStory = storyService.updateStory(id, story);
		return ResponseEntity.ok(updatedStory);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<? extends Object> deleteStory(@PathVariable int id) {
		storyService.deleteStory(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
