package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class QnaException extends RuntimeException {
	private final ErrorCode qnaCode;
	private final String message;

	public QnaException(ErrorCode qnaCode, String message) {
		super(message);
		this.qnaCode = qnaCode;
		this.message = message;

	}
}
