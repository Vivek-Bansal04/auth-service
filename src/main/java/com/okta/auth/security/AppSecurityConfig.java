package com.okta.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Disable cors and csrf
        http.cors().and().csrf().disable();
        http.authorizeRequests(authorize -> authorize
                // these paths will be ignored by the security layer
                .antMatchers(HttpMethod.POST,  "/user/login").permitAll()
                .antMatchers(getIgnorePaths()).permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated());

        // Custom filter to handle jwt authentication
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Exception handler
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

        // Disable session to store user's state
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private String[] getIgnorePaths() {
        final Set<String> uniqueIgnorePaths = new HashSet<>();

        uniqueIgnorePaths.addAll(List.of(
                "/actuator/health","/otp/generate","/hello","/external",
                "/external-after","/nested","/transfer","/execute","/user/validate"));
//        uniqueIgnorePaths.add("/otp/generate");
//        uniqueIgnorePaths.add("/otp/generate");
//        uniqueIgnorePaths.add("/otp/generate");

        // Collect the final list of paths to ignore
        return uniqueIgnorePaths.toArray(String[]::new);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


}
