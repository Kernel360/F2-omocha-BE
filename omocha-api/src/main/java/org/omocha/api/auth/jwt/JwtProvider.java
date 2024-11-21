package org.omocha.api.auth.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private final JwtGenerator jwtGenerator;
	private final JwtUtil jwtUtil;

	private final SecretKey accessKey;
	private final SecretKey refreshKey;
	private final static long ACCESS_EXPIRATION = 1000L * 60L * 60L;
	private final static long REFRESH_EXPIRATION = 1000L * 60L * 60L * 24L;

	public JwtProvider(
		@Value("${jwt.access_secret}") String ACCESS_SECRET,
		@Value("${jwt.refresh_secret}") String REFRESH_SECRET,
		JwtGenerator jwtGenerator,
		JwtUtil jwtUtil
	) {
		this.accessKey = Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
		this.refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
		this.jwtGenerator = jwtGenerator;
		this.jwtUtil = jwtUtil;
	}

	public String generateAccessToken(Long memberId, HttpServletResponse response) {
		String accessToken = jwtGenerator.generateAccessToken(memberId, accessKey, ACCESS_EXPIRATION);
		ResponseCookie cookie = jwtUtil.setTokenToCookie(JwtCategory.ACCESS.getValue(), accessToken);
		response.addHeader("Set-Cookie", cookie.toString());

		return accessToken;
	}

	public String generateRefreshToken(Long memberId, HttpServletResponse response) {
		String refreshToken = jwtGenerator.generateRefreshToken(memberId, refreshKey, REFRESH_EXPIRATION);

		RefreshTokenManager.removeUserRefreshToken(memberId);
		RefreshTokenManager.putRefreshToken(refreshToken, memberId);

		ResponseCookie cookie = jwtUtil.setTokenToCookie(JwtCategory.REFRESH.getValue(), refreshToken);
		response.addHeader("Set-Cookie", cookie.toString());

		return refreshToken;
	}

	public boolean validateAccessToken(String token) {
		return jwtUtil.validateToken(token, accessKey);
	}

	public boolean validateRefreshToken(String token) {
		return jwtUtil.validateToken(token, refreshKey);
	}

	public String resolveTokenFromCookie(HttpServletRequest request, JwtCategory tokenPrefix) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		return jwtUtil.resolveTokenFromCookie(cookies, tokenPrefix);
	}

	public Long getMemberIdFromToken(String accessToken) {
		return Long.parseLong(Jwts.parserBuilder()
			.setSigningKey(accessKey)
			.build()
			.parseClaimsJws(accessToken)
			.getBody()
			.getSubject());
	}

	public void logout(HttpServletResponse response) {
		ResponseCookie accessCookie = jwtUtil.resetTokenToCookie(JwtCategory.ACCESS);
		ResponseCookie refreshCookie = jwtUtil.resetTokenToCookie(JwtCategory.REFRESH);

		response.addHeader("Set-Cookie", accessCookie.toString());
		response.addHeader("Set-Cookie", refreshCookie.toString());
	}
}