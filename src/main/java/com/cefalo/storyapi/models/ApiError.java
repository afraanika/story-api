package com.cefalo.storyapi.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	
	 private HttpStatus status;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	 private LocalDateTime timestamp;
     private List<String> message;
     
	 public ApiError() {
		 timestamp = LocalDateTime.now();
		 message = new ArrayList<>();
	 }

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}
}
