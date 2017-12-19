package com.jeenguyen.demo.oauth.api.services;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		/*
		 * Looking for user "username"
		 */
		String queriedUserName = invoke(username);
		/*
		 * Must match with the form credential
		 */
		return new User(queriedUserName, queriedUserName,
				AuthorityUtils.createAuthorityList("ROLE_USER"));

	}

	public String invoke(String username) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new MyErrorHandler());

		ResponseEntity<String> response = restTemplate.exchange(
				"https://jsonplaceholder.typicode.com/users", HttpMethod.GET,
				null, String.class);

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			JSONArray json = new JSONArray(response.getBody());
			
			for (int i = 0; i < json.length(); i++) {
				if (json.getJSONObject(i).getString("username").equals(username))				
					return username;
			}
		}
		throw new UsernameNotFoundException(String.format(
				"Username %s not found", username));

	}

	class MyErrorHandler extends DefaultResponseErrorHandler {
		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			System.out.println("Error Handling");
		}
	}
}
