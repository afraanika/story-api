package com.cefalo.storyapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {
	private static final long serialVersionUID = 234234523523L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	@Value("${secretKey}")
	private String secretKey;

	public String extractEmail(String token){
		return extractClaims(token, Claims:: getSubject);
	}
	public Date extractExpirationDate(String token){
		return extractClaims(token, Claims :: getExpiration);
	}
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}private Claims extractAllClaims(String token){
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	public Boolean isTokenExpired(String token){
		final Date expiration = extractExpirationDate(token);
		return expiration.before(new Date());
	}
	public String createToken(UserDetails userDetails){
		Map<String, Object> claims = new HashMap<>();
		return generateToken(claims, userDetails.getUsername());
	}

	private String generateToken(Map<String, Object> claims, String subject){
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}public Boolean validateToken(String token, UserDetails userDetails){
		final String username = extractEmail(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}