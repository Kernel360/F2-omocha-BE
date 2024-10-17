package org.auction.client.jwt.util;

import java.security.Key;
import java.util.Arrays;

import org.auction.client.jwt.JwtCategory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUtil {

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

	public String resolveTokenFromCookie(Cookie[] cookies, JwtCategory tokenPrefix) {
		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals(tokenPrefix.getValue()))
			.findFirst()
			.map(Cookie::getValue)
			.orElse("");
	}

	public Cookie resetToken(JwtCategory tokenPrefix) {
		Cookie cookie = new Cookie(tokenPrefix.getValue(), null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		return cookie;
	}
}
