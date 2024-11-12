package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
	private final ErrorCode memberCode;
	private final String message;

	public MemberException(ErrorCode memberCode, String message) {
		super(message);
		this.memberCode = memberCode;
		this.message = message;
	}
}
