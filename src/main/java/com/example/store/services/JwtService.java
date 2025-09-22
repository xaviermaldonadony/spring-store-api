package com.example.store.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    final long tokenExpiration = 86400; // one day

    public String generateToken(String email) {
        return  Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 100 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor("secret".getBytes()))
                .compact();
    }
}
