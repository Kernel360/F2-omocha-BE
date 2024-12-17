package org.omocha.domain.notification.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

import lombok.Getter;

@Getter
public class NotificationException extends OmochaException {
	private final ErrorCode errorCode;
	private final String message;

	public NotificationException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
