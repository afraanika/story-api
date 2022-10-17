package com.cefalo.storyapi.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cefalo.storyapi.dto.UserDTO;
import com.cefalo.storyapi.models.User;

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
	public List<UserDTO> iterableUserDTO(List<User> users) {
		return users.stream()
				.map(u -> entityToDTO(u))
				.collect(Collectors.toList());
	}
}
