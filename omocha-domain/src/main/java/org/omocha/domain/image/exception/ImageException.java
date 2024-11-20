package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.Getter;

@Getter
public class ImageException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;

	public ImageException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
