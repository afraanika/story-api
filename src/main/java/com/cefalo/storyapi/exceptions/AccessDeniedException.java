package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class AccessDeniedException  extends RuntimeException {
	
	public AccessDeniedException(Class activeClass) {
		super(AccessDeniedException.generateMessage(activeClass.getSimpleName()));
	}

	private static String generateMessage(String entity) {
		return "Access Denied for " + entity;
	}

}
