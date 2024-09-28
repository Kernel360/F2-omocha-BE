package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	// EXPLAIN: 200 OK 응답 코드
	AUCTION_CREATE_SUCCESS(HttpStatus.OK, "경매가 성공적으로 생성되었습니다."),
	AUCTION_DELETE_SUCCESS(HttpStatus.OK, "경매가 성공적으로 삭제되었습니다."),
	AUCTION_DETAIL_SUCCESS(HttpStatus.OK, "경매 상세 정보 조회 성공"),

	// EXPLAIN: 400 Bad Request 응답 코드
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

	// EXPLAIN: 404 Not Found 응답 코드
	NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),

	// EXPLAIN: 500 Internal Server Error 응답 코드
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	public int getStatusCode() {
		return httpStatus.value();
	}
}