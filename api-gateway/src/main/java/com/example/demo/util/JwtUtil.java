package com.example.demo.util;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtUtil {

	private final SecretKey signKey;

	public JwtUtil(@Value("${jwt.secret.key}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.signKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public Mono<Void> validateToken(ServerWebExchange exchange, String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(signKey).build().parseSignedClaims(token).getPayload();

			String username = claims.getSubject();

			// Optional: forward identity downstream
			exchange.getRequest().mutate().header("X-User-Name", username).build();

			return Mono.empty();

		} catch (JwtException | IllegalArgumentException e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
	}
}