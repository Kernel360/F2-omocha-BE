package org.omocha.domain.exception.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	;

	private final HttpStatus httpStatus;
	private final String resultMsg;

	public String getErrorMsg(Object... arg) {
		return String.format(resultMsg, arg);
	}

	public String getHttpStatus(Object... arg) {
		return String.format(String.valueOf(httpStatus), arg);
	}
}
