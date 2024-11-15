package org.omocha.domain.qna.exception;

import org.omocha.domain.common.code.ErrorCode;

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
