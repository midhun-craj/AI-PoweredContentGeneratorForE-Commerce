package com.mcr.userservice.security.jwt;

import com.mcr.userservice.security.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(UserDetailsImpl userDetails, boolean isAccessToken) {
        long expirationTime = isAccessToken ? accessTokenExpiration : refreshTokenExpiration;

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("userId", userDetails.getUser().getId())
                .claim("role", "USER")
                .claim("username", userDetails.getUser().getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationTime)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
