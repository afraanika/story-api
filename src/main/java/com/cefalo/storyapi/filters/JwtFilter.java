package com.cefalo.storyapi.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cefalo.storyapi.models.User;
import com.cefalo.storyapi.services.UserDetailsServiceImp;
import com.cefalo.storyapi.services.UserService;
import com.cefalo.storyapi.utils.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	final String AUTHORIZATION_HEADER = "Authorization";
	final String TOKEN_PREFIX = "Bearer ";
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImp userDetailsServiceImp;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		 final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

		 String email = null;
	     String jwt = null;

	     if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
	    	 jwt = authorizationHeader.substring(7);
	         email = jwtUtil.extractEmail(jwt);
	     }

	     if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    	 UserDetails user = this.userDetailsServiceImp.loadUserByUsername(email);
	    	 if (jwtUtil.validateToken(jwt, user)) {
	    		 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
	    				 new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	    		 usernamePasswordAuthenticationToken.setDetails(
	    				 new WebAuthenticationDetailsSource().buildDetails(request));
	    		 SecurityContextHolder.getContext().setAuthentication(
	    				 usernamePasswordAuthenticationToken);
	            }
	        }
	        filterChain.doFilter(request, response);
	}

}
