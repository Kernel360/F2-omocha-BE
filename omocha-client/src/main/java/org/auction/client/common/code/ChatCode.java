package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatCode {

	// EXPLAIN: 200 OK 응답 코드
	CHATROOM_LIST_SUCCESS(HttpStatus.OK, "채팅방 목록 조회 성공"),
	CHAT_MESSAGES_FETCH_SUCCESS(HttpStatus.OK, "메세지 리스트 조회 성공"),
	CHATROOM_CREATE_SUCCESS(HttpStatus.CREATED, "채팅방 생성 성공"),
	CHATROOM_DETAILS_AND_MESSAGES_SUCCESS(HttpStatus.OK, "채팅방 상세 정보 및 전체 메시지 조회 성공"),

	// EXPLAIN : 400 ERROR 응답 코드
	SELLER_IS_BUYER(HttpStatus.BAD_REQUEST, "판매자와 구매자가 동일할 수 없습니다."),

	// EXPLAIN: 403 ERROR 응답 코드
	CHAT_MESSAGES_FETCH_FAIL(HttpStatus.FORBIDDEN, "메세지 리스트 조회 실패"),
	CHATROOM_ACCESS_FAIL(HttpStatus.FORBIDDEN, "채팅방에 접근 거부되었습니다"),
	CHATROOM_ACCESS_UNAUTHORIZED(HttpStatus.FORBIDDEN, "채팅방에 접근 권한이 없습니다"),

	// EXPLAIN: 404 ERROR 응답 코드
	CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다"),

	// EXPLAIN : 409 ERROR 응답 코드
	CHATROOM_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 채팅방입니다."),

	// EXPLAIN: 500 SERVER ERROR
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
