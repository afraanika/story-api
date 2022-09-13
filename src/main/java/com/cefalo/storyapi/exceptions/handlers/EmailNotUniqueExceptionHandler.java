package com.cefalo.storyapi.exceptions.handlers;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.exceptions.EmailNotUniqueException;
import com.cefalo.storyapi.models.ApiError;

public class EmailNotUniqueExceptionHandler 
	extends ResponseEntityExceptionHandler 
	implements RestExceptionHandler<EmailNotUniqueException> {

	@Override
	@ExceptionHandler(EmailNotUniqueException.class)
	public ResponseEntity<Object> handleException(EmailNotUniqueException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(Arrays.asList(e.getMessage()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
