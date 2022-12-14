package com.cefalo.storyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cefalo.storyapi.filters.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private JwtFilter jwtFilter;

	@Value("${apiPrefix}")
	private String apiVersion;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
			http	
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(apiVersion + "/signin", apiVersion + "/signup").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, apiVersion + "/stories/**", apiVersion + "/users/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
