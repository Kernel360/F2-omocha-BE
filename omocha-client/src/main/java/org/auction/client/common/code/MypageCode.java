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
	MY_BIDDING_LIST_SUCCESS(HttpStatus.OK, "입찰 내역을 성공적으로 조회하였습니다.");

	// EXPLAIN: 404 Not Found 응답 코드

	// EXPLAIN: 500 Internal Server Error 응답 코드

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
