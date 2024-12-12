package org.omocha.api.auth.jwt;

import java.security.Key;

import javax.crypto.SecretKey;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.exception.InvalidRefreshTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

	private final JwtGenerator jwtGenerator;
	private final RefreshTokenManager refreshTokenManager;

	private final SecretKey accessKey;
	private final SecretKey refreshKey;
	private final long ACCESS_EXPIRATION = 1000L * 60 * 60 * 24;
	private final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

	public JwtProvider(
		@Value("${jwt.access_secret}") String ACCESS_SECRET,
		@Value("${jwt.refresh_secret}") String REFRESH_SECRET,
		JwtGenerator jwtGenerator,
		RefreshTokenManager refreshTokenManager
	) {
		this.accessKey = Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
		this.refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
		this.jwtGenerator = jwtGenerator;
		this.refreshTokenManager = refreshTokenManager;
	}

	public AuthDto.JwtResponse generateToken(Long memberId) {
		String accessToken = jwtGenerator.generateToken(memberId, "ACCESS", accessKey, ACCESS_EXPIRATION);
		String refreshToken = jwtGenerator.generateToken(memberId, "REFRESH", refreshKey, REFRESH_EXPIRATION);

		refreshTokenManager.putRefreshToken(refreshToken, memberId);

		return new AuthDto.JwtResponse(accessToken, refreshToken);
	}

	public AuthDto.JwtResponse reissueToken(MemberCommand.ReissueToken reissueTokenCommand) {
		Long memberIdByRefreshToken = refreshTokenManager.findMemberIdByRefreshToken(
			reissueTokenCommand.refreshToken());

		if (memberIdByRefreshToken == null || !validateRefreshToken(reissueTokenCommand.refreshToken())) {
			throw new InvalidRefreshTokenException(reissueTokenCommand.refreshToken());
		}

		refreshTokenManager.removeRefreshToken(reissueTokenCommand.refreshToken());
		return generateToken(memberIdByRefreshToken);
	}

	public boolean validateAccessToken(String token) {
		return validateToken(token, accessKey);
	}

	private boolean validateRefreshToken(String token) {
		return validateToken(token, refreshKey);
	}

	public Long getMemberIdFromToken(String accessToken) {
		return Long.parseLong(Jwts.parserBuilder()
			.setSigningKey(accessKey)
			.build()
			.parseClaimsJws(accessToken)
			.getBody()
			.getSubject());
	}

	// TODO: 추후 에러 핸들링 리팩토링
	private boolean validateToken(String token, Key secretKey) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.warn("JWT token is expired: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("JWT claims string is empty: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("JWT token is unsupported: {}", e.getMessage());
		} catch (SecurityException | SignatureException e) {
			log.warn("Invalid JWT signature: {}", e.getMessage());
		}

		return false;
	}
}