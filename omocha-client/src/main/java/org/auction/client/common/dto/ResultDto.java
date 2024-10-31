package org.auction.client.common.dto;

public record ResultDto<T>(int statusCode, String resultMsg, T resultData) {

	public ResultDto(int statusCode, String resultMsg) {
		this(statusCode, resultMsg, null);
	}

	public static <T> ResultDto<T> res(final int statusCode, final String resultMsg) {
		return new ResultDto<>(statusCode, resultMsg, null);
	}

	public static <T> ResultDto<T> res(final int statusCode, final String resultMsg, final T t) {
		return new ResultDto<>(statusCode, resultMsg, t);
	}
}