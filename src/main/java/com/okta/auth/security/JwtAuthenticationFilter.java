package com.okta.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JWTUtil jwtUtil;

    //this filter is used in case of monolithic architecture
    //we have used an endpoint for validating in case of microservice architecture
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
//        final String token = getBearerToken(request.getHeader("Authorization"));
//        String identifier = null;
//        if(token != null){
//            identifier = jwtUtil.extractIdentifier(token);
//        }
//
//        if(identifier != null && jwtUtil.validateToken(token, identifier) && SecurityContextHolder.getContext().getAuthentication() == null){
//
//            UsernamePasswordAuthenticationToken apiToken = new UsernamePasswordAuthenticationToken(identifier, null, Collections.emptyList());
//
//            SecurityContextHolder.getContext().setAuthentication(apiToken);
//
//        }
        filterChain.doFilter(request, response);
    }

    private String getBearerToken(String token) {
        if (StringUtils.hasLength(token)) {
            return token.startsWith(BEARER_PREFIX) ? token.substring(BEARER_PREFIX.length()) : token;
        }
        return null;
    }
}
