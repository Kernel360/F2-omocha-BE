package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class MemberNotFoundExceptionForEmail extends MemberException {
	public MemberNotFoundExceptionForEmail(String email) {
		super(ErrorCode.MEMBER_NOT_FOUND,
			"회원을 찾을 수 없습니다. email : " + email
		);
	}
}
