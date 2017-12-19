package com.jeenguyen.demo.oauth.server.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class CustomLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{

	public CustomLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}
	
	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response,
	        AuthenticationException exception) {
	    String url = super.determineUrlToUseForThisRequest(request, response, exception);
	    return url + "?" + request.getQueryString();
	}

}
