package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class AccessDeniedException  extends RuntimeException {
	
	public AccessDeniedException(Class class1, String tittle) {
		super(AccessDeniedException.generateMessage(class1.getSimpleName(), tittle));
	}

	private static String generateMessage(String entity, String tittle) {
		return "Access Denied : " + entity + " unable to modify/delete the story tittled: " + tittle  ;
	}
}
