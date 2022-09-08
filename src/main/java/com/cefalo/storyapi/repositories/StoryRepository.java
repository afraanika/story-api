package com.cefalo.storyapi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cefalo.storyapi.models.Story;

@Repository
public interface StoryRepository extends CrudRepository<Story, Integer> {
	
}
