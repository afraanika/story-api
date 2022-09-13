package com.cefalo.storyapi.utils;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cefalo.storyapi.dto.StoryDTO;
import com.cefalo.storyapi.models.Story;

@Component
public class StoryConverterUtil {

	public StoryDTO entityToDTO(Story story) {
		StoryDTO dto = new StoryDTO();
		dto.setId(story.getId());
		dto.setTittle(story.getTittle());
		dto.setDescription(story.getDescription());
		dto.setAuthorName(story.getUser().getEmail());
		dto.setCreated_Date(story.getCreated_Date());
		return dto;
	}

	public Iterable<StoryDTO> iterableStoryDTO(Iterable<Story> stories) {
		return StreamSupport.stream(stories.spliterator(), false).collect(Collectors.toList()).stream()
				.map(u -> entityToDTO(u)).collect(Collectors.toList());
	}

}
