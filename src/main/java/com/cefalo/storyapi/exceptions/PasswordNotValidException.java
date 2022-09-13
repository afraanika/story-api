package com.cefalo.storyapi.exceptions;

@SuppressWarnings("serial")
public class PasswordNotValidException extends RuntimeException {
	
	public PasswordNotValidException() {
		super("Password"+ " : " + "Not Valid. Please put valid password : must contain one capital letter, one small letter and one number");
	}
}
