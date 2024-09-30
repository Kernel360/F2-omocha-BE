package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BidCode {

	/*// EXPLAIN: 404 ERROR
	IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다"),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	AUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "경매 게시물을 찾을 수 없습니다."),

	// EXPLAIN: 415 ERROR
	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type 입니다."),

	// EXPLAIN: 500 SERVER ERROR
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다."),
	IMAGE_DELETION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제 중 오류가 발생했습니다.");*/


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
