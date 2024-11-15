package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MemberInvalidPasswordException extends MemberException {
	public MemberInvalidPasswordException(Long memberId) {
		super(
			ErrorCode.INVALID_PASSWORD,
			"비밀번호가 일치하지 않습니다. memberId : " + memberId
		);
	}
}
