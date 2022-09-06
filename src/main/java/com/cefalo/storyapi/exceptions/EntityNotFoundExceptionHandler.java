package com.cefalo.storyapi.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.controllers.RestExceptionHandler;
import com.cefalo.storyapi.models.ApiError;

@Component
public class EntityNotFoundExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<EntityNotFoundException> {

	@Override
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleException(EntityNotFoundException e) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(Arrays.asList(e.getMessage()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
