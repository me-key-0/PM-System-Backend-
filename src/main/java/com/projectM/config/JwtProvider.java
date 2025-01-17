package com.projectM.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {
        return Jwts.builder().setIssuedAt(new Date())
                .setSubject(auth.getName())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();
    }
    public static String getEmailFromToken(String jwt ) {

        JwtParser parser = Jwts.parser().setSigningKey(key).build();
        Jws<Claims> jws = parser.parseSignedClaims(jwt.trim());
        Claims claims = jws.getPayload();

        return String.valueOf(claims.get("email"));
    }
}