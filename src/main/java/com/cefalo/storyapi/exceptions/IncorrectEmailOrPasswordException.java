package com.cefalo.storyapi.exceptions;

public class IncorrectEmailOrPasswordException extends RuntimeException {

	public IncorrectEmailOrPasswordException() {
        super("Incorrect Email Or Password. Please Put Correct Email and Password");
    }
}
