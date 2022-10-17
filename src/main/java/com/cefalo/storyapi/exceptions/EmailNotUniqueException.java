package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class EmailNotUniqueException extends RuntimeException {
	public EmailNotUniqueException(String parameter, String value) {
		super(parameter + " : " + value + " already exists");
	}
}

