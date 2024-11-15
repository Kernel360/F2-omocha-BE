package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class MemberInvalidMemberException extends MemberException {
	public MemberInvalidMemberException(Long memberId) {
		super(
			ErrorCode.INVALID_MEMBER,
			"회원이 일치하지 않습니다. memberId : " + memberId
		);
	}
}
