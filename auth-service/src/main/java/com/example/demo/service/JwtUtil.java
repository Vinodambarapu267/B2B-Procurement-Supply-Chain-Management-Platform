package com.example.demo.service;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.utility.Roles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	public static String SECRET;
	  @Value("${jwt.secret.key}")
	    public void setSecret(String secret) {
	        SECRET = secret;
	    }
	 private SecretKey getSignKey() {
		    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		    return Keys.hmacShaKeyFor(keyBytes);
		}
    public String generateToken(String username, Roles roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        final String extractedUsername = extractUsername(token);
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}