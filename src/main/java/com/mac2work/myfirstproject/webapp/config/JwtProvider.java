package com.mac2work.myfirstproject.webapp.config;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtProvider {

	private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
	
	public String generateToken(String login) {
		return Jwts.builder()
				.subject(login)
				.expiration(Date.from(LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.UTC)))
				.signWith(SECRET_KEY)
				.compact();
	}
	
	public boolean isTokenValid(String token, String username) {
		final String tokenUsername = extractUsername(token);
		return (tokenUsername.equals(username)) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String extractUsername(String token) {
		
		Claims payload = getPayload(token);
		return payload.getSubject();
	}
	
	public Date extractExpiration(String token) {
		Claims payload = getPayload(token);
		return payload.getExpiration();
	}
	
	private Claims getPayload(String token) {
		return Jwts.parser()
				.verifyWith(SECRET_KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}