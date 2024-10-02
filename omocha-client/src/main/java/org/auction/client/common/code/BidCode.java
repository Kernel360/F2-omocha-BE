package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BidCode {

	// EXPLAIN: 200 OK 응답 코드
	BIDDING_ADD_SUCCESS(HttpStatus.OK, "성공적으로 입찰되었습니다."),
	BIDDING_GETLIST_SUCCESS(HttpStatus.OK, "성공적으로 입찰을 불러왔습니다.");

	// EXPLAIN: 404 Not Found 응답 코드

	// EXPLAIN: 500 Internal Server Error 응답 코드

	private final HttpStatus httpStatus;
	private final String message;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
