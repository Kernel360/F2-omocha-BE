package org.omocha.domain.chat.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

import lombok.Getter;

@Getter
public class ChatException extends OmochaException {
	private final ErrorCode errorCode;
	private final String message;

	public ChatException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
