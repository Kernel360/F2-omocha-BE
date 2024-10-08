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

	// EXPLAIN: 404 Not Found 응답 코드

	// EXPLAIN: 500 Internal Server Error 응답 코드

	// TODO : 관련 공부 후 재분류
	// EXPLAIN: 추가 분류 필요
	BIDPRICE_BELOW_STARTPRICE(HttpStatus.UNPROCESSABLE_ENTITY, "입찰 가격이 시작 가격보다 낮습니다."),
	BIDPRICE_BELOW_HIGHESTBID(HttpStatus.UNPROCESSABLE_ENTITY, "입찰 가격이 입찰 최고가보다 같거나 낮습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
