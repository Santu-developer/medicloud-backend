package com.medicloud.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	private SecretKey signingKey;

	@PostConstruct
	private void init() {
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String email, Long shopId, String role) {
		return Jwts.builder()
				.subject(email)
				.claim("shopId", shopId)
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(signingKey)
				.compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Long extractShopId(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("shopId", Long.class);
	}

	public String extractRole(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("role", String.class);
	}

	public boolean isTokenValid(String token, String username) {
		return username.equals(extractUsername(token)) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	private <T> T extractClaim(String token, Function<Claims, T> resolver) {
		final Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}