package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class PasswordNotValidException extends RuntimeException {
	
	public PasswordNotValidException(String parameter, String message) {
		super(PasswordNotValidException.generateMessage(parameter, message));
	}

	private static String generateMessage(String parameter, String message) {
		return parameter + " : " + message;
	}

}
