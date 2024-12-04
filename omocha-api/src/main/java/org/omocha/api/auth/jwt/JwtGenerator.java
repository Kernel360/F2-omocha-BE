package org.omocha.api.auth.jwt;

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

	public String generateToken(
		Long memberId,
		String TokenType,
		Key key,
		long EXPIRATION
	) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setClaims(createClaims(memberId, TokenType))
			.setSubject(String.valueOf(memberId))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
			.signWith(key)
			.compact();
	}

	private Map<String, Object> createHeader() {
		Map<String, Object> header = new HashMap<>();
		header.put("typ", "JWT");
		header.put("alg", "HS512");

		return header;
	}

	private Map<String, Object> createClaims(Long memberId, String tokenType) {
		MemberInfo.MemberDetail memberDetail = memberService.retrieveMember(memberId);

		Map<String, Object> claims = new HashMap<>();
		claims.put("TokenType", tokenType);
		claims.put("Role", memberDetail.role());

		return claims;
	}
}