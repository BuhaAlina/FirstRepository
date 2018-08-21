package com.test.Test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(POST, "/logout").permitAll()
                .antMatchers(POST, "/create/user").permitAll()
                .antMatchers(GET, "/users").permitAll()
                .antMatchers(GET, "/user").permitAll()
                .antMatchers(PUT, "/user").permitAll()
                .antMatchers(POST, "/reset").permitAll()
                .antMatchers(GET, "/categories").permitAll()
                .antMatchers(POST, "/create/category").permitAll()
                .antMatchers(POST, "/update/category").permitAll()
                .antMatchers(POST, "/create/course").permitAll()
                .antMatchers(GET, "/courses").permitAll()
                .antMatchers(GET, "/course").permitAll()
                .antMatchers(POST, "/create/chapter").permitAll()
                .antMatchers(GET, "/chapters").permitAll()
                .antMatchers(GET, "/chapter").permitAll()
                .antMatchers(POST, "/create/question").permitAll()
                .antMatchers(GET, "/questions").permitAll()
                .antMatchers(PUT, "/category").permitAll()
                .antMatchers(PUT, "/course").permitAll()
                .antMatchers(PUT, "/chapter").permitAll()
                .antMatchers(PUT, "/question").permitAll()
                .antMatchers(GET, "/answer").permitAll()
                .antMatchers(GET, "/answers").permitAll()
                .antMatchers(POST, "/create/answer").permitAll()
                .antMatchers(DELETE, "/user").permitAll()
                .antMatchers(POST, "/answer/point").permitAll()
                .antMatchers(POST, "/create/admin").permitAll()
                .antMatchers(DELETE, "/delete/category").permitAll()
                //.antMatchers(GET, "/login").permitAll()   //.hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()//allow CORS option calls
                .antMatchers("/resources/**").permitAll()
                .anyRequest().fullyAuthenticated() //add for default logout
                .and().httpBasic()
                .and().logout().disable()
                .csrf().disable();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").antMatchers(HttpMethod.OPTIONS, "/**");
    }




}
