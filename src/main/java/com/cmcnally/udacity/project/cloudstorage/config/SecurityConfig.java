package com.cmcnally.udacity.project.cloudstorage.config;


/*
    Security configuration class to define the authentication config and HTTP request security
 */

import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig  {


//    // Configure the security requirements for HTTP requests
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // Permit all users from accessing the signup page and site resources
//        // Permit only authenticated users from accessing all other pages
//        http.authorizeRequests()
//                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
//                .anyRequest().authenticated();
//
//        // Set the login page and permit all users to access it
//        http.formLogin()
//                .loginPage("/login")
//                .permitAll();
//
//        // Set the home page to be loaded on a successful login
//        http.formLogin()
//                .defaultSuccessUrl("/home", true);
//
//    }
}
