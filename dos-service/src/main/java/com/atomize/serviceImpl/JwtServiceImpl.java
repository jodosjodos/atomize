package com.atomize.serviceImpl;

import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.SignatureException;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    @Override
    public String generateToken(UserDetails userDetails) {
        return null;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extractclaims, UserDetails userDetails) {
        return null;
    }


    //    supporter methods
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {

            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (JwtException e){
            throw new ApiRequestException("token parsing failed  " + e.getMessage(), HttpStatus.UNAUTHORIZED);

        }

    }

    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode("3D41112DCF3F434B58C192E379DF13D41112DCF3F434B58C192E379DF1");
        return Keys.hmacShaKeyFor(key);
    }
}
