package com.cefalo.storyapi.repositories;

import com.cefalo.storyapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cefalo.storyapi.models.Story;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findAllByUser(Iterable<User> user);
}
