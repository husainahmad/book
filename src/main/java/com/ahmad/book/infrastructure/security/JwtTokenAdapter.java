package com.ahmad.book.infrastructure.security;

import com.ahmad.book.application.port.out.JwtTokenPort;
import com.ahmad.book.infrastructure.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements JwtTokenPort {

    private final JwtProperties jwtProperties;

    @Override
    public String generateToken(String user, String role) {
        return Jwts.builder()
                .subject(user)
                .issuedAt(new Date(System.currentTimeMillis()))
                .claim("roles", role)
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpired()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
                .compact();
    }

    @Override
    public boolean validate(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", String.class);
    }


}
