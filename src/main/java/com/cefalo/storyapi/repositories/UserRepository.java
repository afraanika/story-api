package com.cefalo.storyapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cefalo.storyapi.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	public Optional<User> findByEmail(String email);
	
}
