package com.cefalo.storyapi.exceptions.handlers;

import com.cefalo.storyapi.exceptions.EntityNotFoundException;
import com.cefalo.storyapi.exceptions.IncorrectEmailOrPasswordException;
import com.cefalo.storyapi.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

public class IncorrectEmailOrPasswordExceptionHandler extends ResponseEntityExceptionHandler implements RestExceptionHandler<IncorrectEmailOrPasswordException> {

    @Override
    @ExceptionHandler(IncorrectEmailOrPasswordException.class)
    public ResponseEntity<Object> handleException(IncorrectEmailOrPasswordException e) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage(Arrays.asList(e.getMessage()));
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
