package com.jeenguyen.demo.oauth.server.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jeebb on 20/11/2014.
 */
@Controller
@RequestMapping("/")
public class AuthController {

    public static final String login = "login";

    private static final String authorizationPage = "forward:/oauth/authorize";
    
    @RequestMapping
    @ResponseBody
    public String index() {
        return "Authorization Server !!!";
    }

    @RequestMapping("/login")
	public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request, 
			HttpServletResponse response, @CookieValue(value="authentication", required=false) String cookieAuthentication) throws Exception {
		
    	/*
    	 * Check authentication
    	 */
    	model.putAll(request.getParameterMap());
    	
    	if (cookieAuthentication!=null)
    		return new ModelAndView(authorizationPage, model);
    	else
    		return new ModelAndView(login, model);

	}

}
