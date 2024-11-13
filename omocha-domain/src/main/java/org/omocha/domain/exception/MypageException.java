package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class MypageException extends RuntimeException {
	private final ErrorCode mypageCode;
	private final String message;

	public MypageException(ErrorCode mypageCode, String message) {
		super(message);
		this.mypageCode = mypageCode;
		this.message = message;
	}
}
