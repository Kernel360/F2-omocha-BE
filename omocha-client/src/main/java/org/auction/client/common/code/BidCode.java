package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BidCode {

	// EXPLAIN: 200 OK 응답 코드
	BIDDING_CREATE_SUCCESS(HttpStatus.OK, "성공적으로 입찰되었습니다."),
	BIDDING_GET_SUCCESS(HttpStatus.OK, "성공적으로 입찰을 불러왔습니다."),
	NOW_PRICE_GET_SUCCESS(HttpStatus.OK, "성공적으로 현재가를 불러왔습니다."),

	// EXPLAIN : 400 ERROR 응답 코드
	NO_BIDS_FOUND(HttpStatus.BAD_REQUEST, "입찰이 존재하지 않습니다."),
	SELF_BID_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자신의 경매에 입찰을 걸 수 없습니다."),
	INVALID_BID_UNIT(HttpStatus.BAD_REQUEST, "입찰 금액이 입찰 단위에 유효하지 않습니다."),
	BID_PRICE_TOO_LOW(HttpStatus.BAD_REQUEST, "입찰 가격이 시작 가격보다 낮습니다."),
	BID_PRICE_NOT_HIGHER(HttpStatus.BAD_REQUEST, "입찰 가격이 입찰 최고가보다 같거나 낮습니다.");

	// EXPLAIN: 404 Not Found 응답 코드

	// EXPLAIN: 500 Internal Server Error 응답 코드

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
