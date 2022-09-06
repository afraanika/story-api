package com.cefalo.storyapi.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException(Class class1, String... serachParamsMap) {
		super(EntityNotFoundException.generateMessage(class1.getSimpleName(), serachParamsMap[0], serachParamsMap[1]));
	}

	private static String generateMessage(String entity, String key, String value) {
		return entity + " not found with " + key + " : " + value;
	}
	
}
