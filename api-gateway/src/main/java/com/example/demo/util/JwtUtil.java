package com.example.demo.util;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final SecretKey signKey;

	 public JwtUtil(@Value("${jwt.secret.key}") String secret) {
	        this.signKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	    }

	    public void validateToken(String token) {
	        Jwts.parser()
	                .verifyWith(signKey)
	                .build()
	                .parseSignedClaims(token);
	    }
}