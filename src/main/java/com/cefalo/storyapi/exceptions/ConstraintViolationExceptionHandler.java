package com.cefalo.storyapi.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cefalo.storyapi.models.ApiError;

@Component
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<ConstraintViolationException> {

	@Override
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleException(ConstraintViolationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		List<String> errors =  new ArrayList<>();
		for(ConstraintViolation<?> violationException: e.getConstraintViolations()) {
			errors.add(violationException.getPropertyPath()+": "+violationException.getMessageTemplate());
		}
		apiError.setMessage(errors);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
