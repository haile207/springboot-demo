package com.demoSB.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                // No need authentication.
                .antMatchers("/accounts/**").permitAll() //
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                // Need authenticated
                .antMatchers("/admin/**").authenticated()
                //
                .and()
                //
                // Add Filter 1 - JWTLoginFilter
                //
                .addFilterBefore(new StringTokenLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
        //
        // Add Filter 2 - JWTAuthenticationFilter
        //
        //.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }
}


