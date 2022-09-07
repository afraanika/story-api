package com.cefalo.storyapi.models;

public class JwtResponse {
	
	private String jwtTokenString;
	
	public JwtResponse(String jwtTokenString) {
		super();
		this.jwtTokenString = jwtTokenString;
	}

	public String getJwtTokenString() {
		return jwtTokenString;
	}

	public void setJwtTokenString(String jwtTokenString) {
		this.jwtTokenString = jwtTokenString;
	}
	
	
}
