package com.atomize.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
// token service
public interface JwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token , UserDetails userDetails);

    String generateRefreshToken(Map<String,Object> extractclaims, UserDetails userDetails);
}
