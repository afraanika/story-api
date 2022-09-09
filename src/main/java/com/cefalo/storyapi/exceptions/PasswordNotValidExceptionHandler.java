package com.cefalo.storyapi.exceptions;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.models.ApiError;

@Component
public class PasswordNotValidExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<PasswordNotValidException> {

	@Override
	@ExceptionHandler(PasswordNotValidException.class) 
	public ResponseEntity<Object> handleException(PasswordNotValidException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(Arrays.asList(e.getMessage()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
