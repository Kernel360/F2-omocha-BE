package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberCode {

	// EXPLAIN: 200 OK 응답 코드
	MEMBER_CREATE_SUCCESS(HttpStatus.OK, "회원을 성공적으로 생성하였습니다."),
	MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료되었습니다."),
	MEMBER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 완료되었습니다."),
	VALIDATE_EMAIL_SUCCESS(HttpStatus.OK, "사용 가능한 이메일입니다."),

	// EXPLAIN: 400 ERROR
	MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	INVALID_MEMBER(HttpStatus.BAD_REQUEST, "회원이 일치하지 않습니다."),

	// EXPLAIN: 404 ERROR
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
