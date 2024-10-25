package org.omocha.api.common.response;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Builder;

@Builder
public record ResultDto<T>(Result result, String resultMsg, T resultData, String errorCode, String errorName) {

	public static <T> ResultDto<T> success(T data, String resultMsg) {
		return (ResultDto<T>)ResultDto.builder()
			.result(Result.SUCCESS)
			.resultMsg(resultMsg)
			.resultData(data)
			.build();
	}

	public static <T> ResultDto<T> success(T data) {
		return success(data, null);
	}

	public static ResultDto fail(String resultMsg, String errorCode, String errorName) {
		return ResultDto.builder()
			.result(Result.FAIL)
			.resultMsg(resultMsg)
			.errorCode(errorCode)
			.errorName(errorName)
			.build();
	}

	public static ResultDto fail(ErrorCode errorCode) {
		return ResultDto.builder()
			.result(Result.FAIL)
			.resultMsg(errorCode.getErrorMsg())
			.errorCode(errorCode.getHttpStatus())
			.errorName(errorCode.name())
			.build();
	}

	public enum Result {
		SUCCESS, FAIL
	}
}