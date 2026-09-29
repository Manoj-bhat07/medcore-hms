package com.manoj.medcore.security;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION_MILLIS = 30 * 60 * 1000L;

    private final SecretKey signingKey;

    public JwtService(@Value("${jwt.secret}") String encodedSecret) {
        try {
            this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecret));
        } catch (RuntimeException exception) {
            throw new IllegalStateException(
                    "JWT_SECRET must be a Base64-encoded secret of at least 32 bytes", exception);
        }
    }

    public String generateToken(String username, String role) {
        Instant issuedAt = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(issuedAt.plusMillis(ACCESS_TOKEN_EXPIRATION_MILLIS)))
                .signWith(signingKey)
                .compact();
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(parseClaims(token).getSubject());
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public long getExpirationSeconds() {
        return ACCESS_TOKEN_EXPIRATION_MILLIS / 1000;
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}