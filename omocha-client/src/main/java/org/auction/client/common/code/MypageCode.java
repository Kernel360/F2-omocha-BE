package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MypageCode {

	// EXPLAIN: 200 OK 응답 코드

	// EXPLAIN: 404 Not Found 응답 코드

	// EXPLAIN: 500 Internal Server Error 응답 코드

	// EXPLAIN: 추가 분류 필요

	// 유저 정보 반환 상태 코드
	MEMBER_INFO_RETRIEVE_SUCCESS(HttpStatus.OK, "유저 정보를 성공적으로 반환하였습니다."),
	TRANSACTION_AUCTION_LIST_SUCCESS(HttpStatus.OK, "경매 내역을 성공적으로 조회하였습니다."),
	TRANSACTION_BIDDING_LIST_SUCCESS(HttpStatus.OK, "입찰 내역을 성공적으로 조회하였습니다."),

	// EXPLAIN: 400 ERROR
	INVALID_USER_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 유저 요청입니다."),
	UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근이 허용되지 않습니다."),
	FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "이 유저 정보에 접근할 권한이 없습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
