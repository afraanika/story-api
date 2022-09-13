package com.cefalo.storyapi.exceptions.handlers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.models.ApiError;

@Order(1)
@Component
public class SQLIntegrityConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<SQLIntegrityConstraintViolationException> {

	@Override
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class) 
	public ResponseEntity<Object> handleException(SQLIntegrityConstraintViolationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(Arrays.asList(e.getMessage()));
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
}
