package org.auction.client.jwt.application;

import java.security.Key;

import javax.crypto.SecretKey;

import org.auction.client.jwt.JwtCategory;
import org.auction.client.jwt.RefreshToken;
import org.auction.client.jwt.util.JwtGenerator;
import org.auction.client.jwt.util.JwtUtil;
import org.auction.client.member.application.MemberService;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

	private final JwtGenerator jwtGenerator;
	private final JwtUtil jwtUtil;
	private final MemberService memberService;
	private final CustomUserDetailsService customUserDetailsService;

	@Value("${jwt.access_secret}")
	private String ACCESS_SECRET;
	@Value("${jwt.refresh_secret}")
	private String REFRESH_SECRET;
	private SecretKey accessKey;
	private SecretKey refreshKey;
	private final static long ACCESS_EXPIRATION = 1000L * 60L * 60L;
	private final static long REFRESH_EXPIRATION = 1000L * 60L * 60L * 24L;

	@PostConstruct
	public void init() {
		accessKey = Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
		refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
	}

	public String generateAccessToken(
		HttpServletResponse response,
		MemberEntity memberEntity
	) {
		String accessToken = jwtGenerator.generateAccessToken(memberEntity, accessKey, ACCESS_EXPIRATION);
		ResponseCookie cookie = jwtUtil.setTokenToCookie(JwtCategory.ACCESS.getValue(), accessToken);
		response.addHeader("Set-Cookie", cookie.toString());

		return accessToken;
	}

	public String generateRefreshToken(
		HttpServletResponse response,
		MemberEntity memberEntity
	) {
		String refreshToken = jwtGenerator.generateRefreshToken(memberEntity, refreshKey, REFRESH_EXPIRATION);
		RefreshToken.removeUserRefreshToken(memberEntity.getMemberId());
		RefreshToken.putRefreshToken(refreshToken, memberEntity.getMemberId());

		ResponseCookie cookie = jwtUtil.setTokenToCookie(JwtCategory.REFRESH.getValue(), refreshToken);
		response.addHeader("Set-Cookie", cookie.toString());

		return refreshToken;
	}

	public boolean validateAccessToken(
		String token
	) {
		return jwtUtil.validateToken(token, accessKey);
	}

	public boolean validateRefreshToken(
		final String token
	) {
		return jwtUtil.validateToken(token, refreshKey);
	}

	public String resolveTokenFromCookie(
		HttpServletRequest request,
		JwtCategory tokenPrefix
	) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			// throw new JwtTokenNotFoundException(JWT_TOKEN_NOT_FOUND);
			return null;
		}
		return jwtUtil.resolveTokenFromCookie(cookies, tokenPrefix);
	}

	public Authentication getAuthentication(
		String token
	) {
		UserDetails principal = customUserDetailsService.loadUserByUsername(getUserPk(token, accessKey));
		return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
	}

	private String getUserPk(
		String token,
		Key secretKey
	) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public MemberEntity findMemberByRefreshToken(
		String refreshToken
	) {
		Long memberId = RefreshToken.findMemberIdByRefreshToken(refreshToken);
		return memberService.findMember(memberId);
	}

	public void logout(
		MemberEntity requestMember,
		HttpServletResponse response
	) {
		RefreshToken.removeUserRefreshToken(requestMember.getMemberId());

		ResponseCookie accessCookie = jwtUtil.resetTokenToCookie(JwtCategory.ACCESS);
		ResponseCookie refreshCookie = jwtUtil.resetTokenToCookie(JwtCategory.REFRESH);

		response.addHeader("Set-Cookie", accessCookie.toString());
		response.addHeader("Set-Cookie", refreshCookie.toString());
	}
}
