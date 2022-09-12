package com.cefalo.storyapi.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SuppressWarnings("deprecation")
@Component
public class JwtUtil implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	 public String extractEmail(String token) {
		 return extractClaim(token, Claims::getSubject);
	 }

	public Date extractExpiration(String token) {
		return (Date) extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token); 
		return claimsResolver.apply(claims);
	}
	    
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date(0));
	}

	public String generateToken(UserDetails existingUser) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, existingUser.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public Boolean validateToken(String token, UserDetails user) {
		final String email = extractEmail(token);
		return (email.equals(user.getUsername()) && !isTokenExpired(token));
	}
}
