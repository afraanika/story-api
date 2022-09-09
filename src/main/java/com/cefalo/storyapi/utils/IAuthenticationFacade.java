package com.cefalo.storyapi.utils;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
	
	Authentication getAuthentication();

}
