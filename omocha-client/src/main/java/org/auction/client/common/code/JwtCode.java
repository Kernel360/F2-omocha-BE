package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtCode {

	// EXPLAIN: 400 ERROR
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않는 Refresh Token 입니다."),

	// EXPLAIN: 404 ERROR
	JWT_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "JWT 토큰을 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
