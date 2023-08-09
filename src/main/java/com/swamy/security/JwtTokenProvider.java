package com.swamy.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret-key}")
	private String secret;

	@Value("${jwt.expiration-date}")
	private Long expirationDate;

	// 1. generate the token
	public String generateToken(Authentication authentication) {

		String username = authentication.getName();

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + expirationDate);

		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate).signWith(key())
				.compact();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	// 2.get Username From the Token
	public String getUsername(String token) {

		Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	// 3. Validate the Token
	public boolean validateToken(String token) {

		Jwts.parserBuilder().setSigningKey(key()).build().parse(token);

		return true;
	}

}
