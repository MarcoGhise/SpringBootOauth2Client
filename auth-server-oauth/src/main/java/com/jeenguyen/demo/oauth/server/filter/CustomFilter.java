package com.jeenguyen.demo.oauth.server.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jeenguyen.demo.oauth.bean.User;

public class CustomFilter extends OncePerRequestFilter  {

	private AuthenticationManager authenticationManager;
	
	private static final String COOKIE_NAME = "authentication";
	
	protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	
	public CustomFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
	throws ServletException, IOException {
		
		User user = getCookieCredential(request);
		
		if (user != null) {
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					user.getPrincipal(), user.getCredential(),
					AuthorityUtils.createAuthorityList("ROLE_USER"));

			authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
			
			Authentication authResult = this.authenticationManager
					.authenticate(authRequest);
			
			SecurityContextHolder.getContext().setAuthentication(authResult);
		}
		else
		{
			Cookie cookie = new Cookie(COOKIE_NAME, null); 
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
			response.addCookie(cookie);
		}

		filterChain.doFilter(request, response);	
	} 

	private User getCookieCredential(HttpServletRequest httpRequest) throws UnsupportedEncodingException {
		
		Cookie[] cookies = httpRequest.getCookies();

		if (cookies != null)
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					byte[] decodedBytes = Base64.getDecoder().decode(
							URLDecoder.decode(cookie.getValue(), "UTF-8"));
					String decodedString = new String(decodedBytes);
					String[] credentialSplitted = decodedString.split(":");
					return new User(credentialSplitted[0], credentialSplitted[1]);
				}
			}
		
		return null;
	}
}
