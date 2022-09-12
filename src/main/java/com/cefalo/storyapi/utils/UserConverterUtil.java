package com.cefalo.storyapi.utils;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.models.UserDTO;

@Component
public class UserConverterUtil {

	public UserDTO entityToDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setNumber(user.getNumber());
		dto.setEmail(user.getEmail());
		dto.setCreated_Date(user.getCreated_Date());
		return dto;
	}

	public Iterable<UserDTO> iterableUserDTO(Iterable<User> users) {
		return StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList()).stream()
				.map(u -> entityToDTO(u)).collect(Collectors.toList());
	}
	
	public User DTOtoEntity(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setNumber(dto.getNumber());
		user.setEmail(dto.getEmail());
		user.setCreated_Date(user.getCreated_Date());
		return user;
	}

}
