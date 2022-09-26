package com.cefalo.storyapi.utils;

import java.util.List;
import java.util.stream.Collectors;

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
	public List<StoryDTO> iterableStoryDTO(List<Story> stories) {
		return stories.stream()
				.map(u -> entityToDTO(u))
				.collect(Collectors.toList());
	}
}
