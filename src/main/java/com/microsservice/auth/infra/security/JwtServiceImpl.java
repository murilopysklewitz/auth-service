package com.microsservice.auth.infra.security;

import com.microsservice.auth.domain.ports.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey;

    private long jwtExpiration;

    public JwtServiceImpl(@Value("${jwt.secret}") String secretKey,
                          @Value("${jwt.expiration}") long jwtExpiration) {
        this.secretKey = secretKey;
        this.jwtExpiration = jwtExpiration;
    }

    @Override
    public String generateToken(String userId, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .id(UUID.randomUUID().toString())
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }
    @Override
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUserId(String token) {
        Claims claims = extractClaim(token);
        return claims.getSubject();
    }

    @Override
    public String extractRole(String token) {
        Claims claims = extractClaim(token);
        return claims.get("role", String.class);

    }

    @Override
    public String extractJti(String token) {
        Claims claims = extractClaim(token);
        return claims.getId();
    }

    @Override
    public Date extractExpiration(String token) {
        Claims claims = extractClaim(token);
        return claims.getExpiration();
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractClaim(String token) {
        final Claims claims = extractAllClaims(token);
        return claims;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}