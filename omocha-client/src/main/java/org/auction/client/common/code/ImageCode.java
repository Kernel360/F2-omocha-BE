package org.auction.client.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageCode {

	// EXPLAIN: 404 ERROR
	IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾을 수 없습니다."),

	// EXPLAIN: 500 SERVER ERROR
	IMAGE_DELETION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제 중 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String resultMsg;

	// 필요하다면 상태 코드를 반환하는 메서드 추가
	public int getStatusCode() {
		return httpStatus.value();
	}
}
