package com.jeenguyen.demo.oauth.server.conf;

import com.jeenguyen.demo.oauth.api.services.CustomUserDetailsService;
import com.jeenguyen.demo.oauth.server.filter.CustomFilter;
import com.jeenguyen.demo.oauth.server.filter.CustomLoginUrlAuthenticationEntryPoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by jeebb on 19/11/2014.
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http 
    	.addFilterBefore(new CustomFilter(authenticationManagerBean()),BasicAuthenticationFilter.class)
    	.authorizeRequests() 
    	.antMatchers("/login", "/logout.do").permitAll() 
    	.and()
    	.authorizeRequests().antMatchers("/**").authenticated() 
    	.and()
                .formLogin()
                .loginProcessingUrl("/login.do") // default spring oauth login url
                .usernameParameter("uid")
                .passwordParameter("pwd")
                .loginPage("/login")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"))
            .and()
                .userDetailsService(userDetailsService());

    }

}
