package org.auction.client.jwt.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtGenerator {

	public String generateAccessToken(MemberEntity memberEntity, Key accessKey, long ACCESS_EXPIRATION) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setClaims(createClaims(memberEntity))
			.setSubject(memberEntity.getLoginId())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
			.signWith(accessKey)
			.compact();
	}

	public String generateRefreshToken(MemberEntity memberEntity, Key refreshKey, long REFRESH_EXPIRATION) {
		return Jwts.builder()
			.setHeader(createHeader())
			.setSubject(memberEntity.getLoginId())
			.setExpiration(new Date(System.currentTimeMillis() + (REFRESH_EXPIRATION * 24)))
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

	private Map<String, Object> createClaims(MemberEntity memberEntity) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("LoginId", memberEntity.getLoginId());
		claims.put("Role", memberEntity.getRole());
		return claims;
	}
}
