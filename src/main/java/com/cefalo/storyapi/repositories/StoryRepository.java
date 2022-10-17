package com.cefalo.storyapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefalo.storyapi.models.Story;
import com.cefalo.storyapi.models.User;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

	
	
}
