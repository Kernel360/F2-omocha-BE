package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class MemberNotFoundExceptionForId extends MemberException {
	public MemberNotFoundExceptionForId(Long memberId) {
		super(ErrorCode.MEMBER_NOT_FOUND,
			"회원을 찾을 수 없습니다. memberId : " + memberId
		);
	}
}
