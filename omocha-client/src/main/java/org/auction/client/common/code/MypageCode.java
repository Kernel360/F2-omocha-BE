package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MypageCode {

	// EXPLAIN: 200 OK 응답 코드

	MEMBER_INFO_RETRIEVE_SUCCESS(HttpStatus.OK, "유저 정보를 성공적으로 반환하였습니다."),
	MY_AUCTION_LIST_SUCCESS(HttpStatus.OK, "경매 내역을 성공적으로 조회하였습니다."),
	MY_BIDDING_LIST_SUCCESS(HttpStatus.OK, "입찰 내역을 성공적으로 조회하였습니다."),

	MEMBER_INFO_UPDATED(HttpStatus.OK, "멤버 정보 수정이 완료되었습니다."),
	PROFILE_IMAGE_UPDATED(HttpStatus.OK, "프로필 이미지 수정이 완료되었습니다."),
	PASSWORD_UPDATED(HttpStatus.OK, "패스워드 수정이 완료되었습니다."),

	NICKNAME_AVAILABLE(HttpStatus.OK, "사용 가능한 닉네임입니다."),

	// EXPLAIN: 400 Bad Request 응답 코드
	NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "닉네임이 이미 사용 중입니다."),
	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
	INVALID_PHONE_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 전화번호 형식입니다."),
	INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 8자리 이상, 알파벳,특수문자 포함이어야 합니다.");

	// EXPLAIN: 404 Not Found 응답 코드

	// EXPLAIN: 500 Internal Server Error 응답 코드

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
