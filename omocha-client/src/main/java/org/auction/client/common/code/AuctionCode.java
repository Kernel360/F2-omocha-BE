package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionCode {

	// EXPLAIN: 200 OK 응답 코드
	AUCTION_CREATE_SUCCESS(HttpStatus.OK, "경매가 성공적으로 생성되었습니다."),
	AUCTION_DELETE_SUCCESS(HttpStatus.OK, "경매가 성공적으로 삭제되었습니다."),
	AUCTION_DETAIL_SUCCESS(HttpStatus.OK, "경매 상세 정보 조회 성공"),
	AUCTION_LIST_ACCESS_SUCCESS(HttpStatus.OK, "경매 전체 정보 조회 성공"),

	// EXPLAIN: 400 ERROR
	AUCTION_ALREADY_ENDED(HttpStatus.BAD_REQUEST, "이미 종료된 경매입니다."),

	// EXPLAIN: 404 ERROR
	AUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "경매 게시물을 찾을 수 없습니다."),

	// EXPLAIN: 415 ERROR
	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type 입니다."),
	AUCTION_WRONG_STATUS(HttpStatus.UNPROCESSABLE_ENTITY, "경매의 상태가 잘못 되었습니다."),

	// EXPLAIN: 500 SERVER ERROR
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}