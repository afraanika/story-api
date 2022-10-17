package com.cefalo.storyapi.models;

import java.util.Objects;

public class JwtResponse {
	
	private String jwtTokenString;
	
	public JwtResponse(String jwtTokenString) {
		this.jwtTokenString = jwtTokenString;
	}

	public String getJwtTokenString() {
		return jwtTokenString;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		JwtResponse that = (JwtResponse) o;
		return Objects.equals(jwtTokenString, that.jwtTokenString);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(jwtTokenString);
	}
}
