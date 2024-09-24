package org.auction.client.common.dto;

import org.springframework.http.HttpStatus;

public record ResultDto<T>(HttpStatus statusCode, String resultMsg, T resultData) {

	public ResultDto(HttpStatus statusCode, String resultMsg) {
		this(statusCode, resultMsg, null);
	}

	public static <T> ResultDto<T> res(final HttpStatus statusCode, final String resultMsg) {
		return new ResultDto<>(statusCode, resultMsg, null);
	}

	public static <T> ResultDto<T> res(final HttpStatus statusCode, final String resultMsg, final T t) {
		return new ResultDto<>(statusCode, resultMsg, t);
	}
}