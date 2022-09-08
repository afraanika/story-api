package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {
	
	public EntityNotFoundException(Class class1, String... serachParamsMap) {
		super(EntityNotFoundException.generateMessage(class1.getSimpleName(), serachParamsMap[0], serachParamsMap[1]));
	}

	private static String generateMessage(String entity, String key, String value) {
		return entity + " not found with " + key + " : " + value;
	}
	
}
