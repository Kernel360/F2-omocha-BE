package org.omocha.api.common.auth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtGenerator {

	private final MemberService memberService;

	public String generateAccessToken(
		Long memberId,
		Key accessKey,
		long ACCESS_EXPIRATION
	) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setClaims(createClaims(memberId))
			.setSubject(String.valueOf(memberId))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
			.signWith(accessKey)
			.compact();
	}

	public String generateRefreshToken(
		Long memberId,
		Key refreshKey,
		long REFRESH_EXPIRATION
	) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setSubject(String.valueOf(memberId))
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.signWith(refreshKey)
			.compact();
	}

	private Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "JWT");
		header.put("alg", "HS512");

		return header;
	}

	private Map<String, Object> createClaims(Long memberId) {
		MemberInfo.MemberDetail memberDetail = memberService.findMember(memberId);

		Map<String, Object> claims = new HashMap<>();
		claims.put("MemberId", memberId);
		claims.put("Role", memberDetail.role());

		return claims;
	}
}