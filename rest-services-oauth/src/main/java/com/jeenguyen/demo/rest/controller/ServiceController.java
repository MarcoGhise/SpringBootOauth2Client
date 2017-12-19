package com.jeenguyen.demo.rest.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeenguyen.demo.bean.Random;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jeebb on 20/11/2014.
 */
@RestController
@RequestMapping("/services")
public class ServiceController {

	private static final Map<String, String> users = new HashMap<String, String>(){
        {
        	put("Bret", "03/05/2003");
        	put("Antonette", "11/10/2000");
        	put("Samantha", "12/04/2002");
        	put("Karianne", "19/07/2008");
        	put("username", "22/03/2004");
        	put("Leopoldo_Corkery", "01/02/2002");
        	put("Elwyn.Skiles", "12/01/2001");
        	put("Maxime_Nienow", "09/11/2002");
        	put("Delphine", "30/09/2005");
        	put("Moriah.Stanton", "11/07/2009");
        }
    };
	
    @RequestMapping(value = "jsonrandom")
    public Random me() {
    	Random random = new Random();
    	random.setNumber(new SecureRandom().nextInt());
        return random;
    }
    
    @RequestMapping(value = "random")
    public Integer random() {
    	return new SecureRandom().nextInt();        
    }
    
    @RequestMapping(path = "account", method = RequestMethod.GET)
    public Principal oauth(Principal principal) {
        /*
         * Translate the incoming request, which has an access token
         * Spring security takes the incoming request and injects the Java Security Principal
         * The converter inside Spring Security will handle the to json method which the Spring Security
         * Oauth client will know how to read
         *
         * The @EnableResourceServer on the application entry point is what makes all this magic happen.
         * If there is an incoming request token it will check the token validity and handle it accordingly
         */
        return principal;
    }
    
    @RequestMapping(path = "me", method = RequestMethod.GET)
    public Principal me(Principal principal) {    	
    	
    	
    	final Map<String, String> birthday = new HashMap<String, String>();
    	
    	OAuth2Authentication auth = (OAuth2Authentication) principal;
    	
    	birthday.put("birthday", users.get(auth.getName()));
    	
    	auth.setDetails(birthday);
    	
        return principal;
    }

}
