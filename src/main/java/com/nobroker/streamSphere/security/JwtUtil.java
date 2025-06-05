package com.nobroker.streamSphere.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "a-very-secret-key-123456789012345678901234567890"; // 256-bit key
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ Basic token (email only)
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Token with email and profileId
    public String generateToken(String email, Long profileId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("profileId", profileId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractProfileId(String token) {
        Claims claims = extractAllClaims(token);
        Object profileId = claims.get("profileId");
        return profileId != null ? Long.parseLong(profileId.toString()) : null;
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // throws if invalid
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean validateTokenWithProfile(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("profileId") != null;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
