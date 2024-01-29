package com.atomize.config;

import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.errors.jwt.JWtAuntenticationResponse;
import com.atomize.services.JwtService;
import com.atomize.services.UserServiceSecurity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService service;
    private final UserServiceSecurity security;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || authHeader.trim().isEmpty() || !authHeader.startsWith("Bearer")) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           filterChain.doFilter(request,response);
            return;
        }
        try {
            jwt = authHeader.substring(7);
            userEmail = service.extractUserName(jwt);
            // not already authenticated
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails dosDetails = security.userDetailsService().loadUserByUsername(userEmail);
                if (service.isTokenValid(jwt, dosDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dosDetails, null, dosDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            log.info("successfully");
            filterChain.doFilter(request, response);
        } catch (StringIndexOutOfBoundsException e) {
            JWtAuntenticationResponse response1 = new JWtAuntenticationResponse("please provide token in header", e.getMessage(), null, HttpServletResponse.SC_UNAUTHORIZED);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(response1));
        } catch (ApiRequestException e) {

            // thrown in UserServiceSecurity
            JWtAuntenticationResponse response1 = new JWtAuntenticationResponse("user with this email  : not found  please provide token with  valid email , or create new account ", e.getMessage(), e.getStatus(), HttpServletResponse.SC_UNAUTHORIZED);
            log.error("user not found");
            response.setStatus(response1.getStatusCode());
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(response1));
        }

    }
}
