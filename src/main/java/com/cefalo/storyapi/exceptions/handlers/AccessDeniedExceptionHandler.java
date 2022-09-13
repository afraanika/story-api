package com.cefalo.storyapi.exceptions.handlers;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.exceptions.AccessDeniedException;
import com.cefalo.storyapi.models.ApiError;

@Component
public class AccessDeniedExceptionHandler
	extends ResponseEntityExceptionHandler 
	implements RestExceptionHandler<AccessDeniedException> {

	@Override
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleException(AccessDeniedException e) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
		apiError.setMessage(Arrays.asList(e.getMessage()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
