package org.auction.client.jwt.util;

import java.security.Key;
import java.util.Arrays;

import org.auction.client.jwt.JwtCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUtil {

	@Value("${url.domain}")
	private String domain;

	public boolean validateToken(final String token, Key secretKey) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (SecurityException e) {
			log.warn("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.warn("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public ResponseCookie setTokenToCookie(
		String tokenPrefix,
		String token
	) {
		return ResponseCookie.from(tokenPrefix, token)
			.path("/")
			.maxAge(60L * 60L * 24L)
			.httpOnly(true)
			.domain(StringUtils.hasText(domain) ? domain : null)
			.sameSite("None")
			.secure(true)
			.build();
	}

	public ResponseCookie resetTokenToCookie(
		JwtCategory tokenPrefix
	) {
		return ResponseCookie.from(tokenPrefix.getValue(), "")
			.path("/")
			.maxAge(0)
			.httpOnly(true)
			.domain(StringUtils.hasText(domain) ? domain : null)
			.sameSite("None")
			.secure(true)
			.build();
	}

	public String resolveTokenFromCookie(Cookie[] cookies, JwtCategory tokenPrefix) {
		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals(tokenPrefix.getValue()))
			.findFirst()
			.map(Cookie::getValue)
			.orElse("");
	}
}
