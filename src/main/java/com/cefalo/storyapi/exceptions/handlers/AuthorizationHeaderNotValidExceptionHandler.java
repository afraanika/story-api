package com.cefalo.storyapi.exceptions.handlers;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.exceptions.AuthorizationHeaderNotValidException;
import com.cefalo.storyapi.models.ApiError;

@Component
public class AuthorizationHeaderNotValidExceptionHandler 
	extends ResponseEntityExceptionHandler 
	implements RestExceptionHandler<AuthorizationHeaderNotValidException> {

	@Override
	@ExceptionHandler(AuthorizationHeaderNotValidException.class)
	public ResponseEntity<Object> handleException(AuthorizationHeaderNotValidException e) {
		System.out.println("Authorization header");
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
		apiError.setMessage(Arrays.asList(e.getMessage()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
