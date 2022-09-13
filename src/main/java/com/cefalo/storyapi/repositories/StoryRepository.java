package com.cefalo.storyapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefalo.storyapi.models.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
	
}
