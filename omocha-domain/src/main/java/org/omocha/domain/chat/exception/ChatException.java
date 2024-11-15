package org.omocha.domain.chat.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class ChatException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public ChatException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
