package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class InvalidParameterException extends RuntimeException {
	
	public InvalidParameterException(String parameter, String message) {
		super(InvalidParameterException.generateMessage(parameter, message));
	}

	private static String generateMessage(String parameter, String message) {
		return parameter + " : " + message;
	}

}
