package com.sebastianarellano.educative_platform.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private Long timeExpiration;

    //Create token
    public String createToken(Authentication authentication){
        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .claim("authorities",authorities)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + timeExpiration))
                .signWith(getSignatureKey())
                .compact();
    }

    //Validate token
    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSignatureKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Extract username from token
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    //Extract authorities from token
    public String extractAuthorities(String token){
        return getClaims(token).get("authorities", String.class);
    }

    //Get claims
    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Generate signature key
    private SecretKey getSignatureKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
