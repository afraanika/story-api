package com.cefalo.storyapi.exceptions;

public class AuthorizationHeaderNotValidException extends RuntimeException {
	
	public AuthorizationHeaderNotValidException(String message) {
		super(message);
	}

}
