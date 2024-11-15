package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MemberNotFoundExceptionForId extends MemberException {
	public MemberNotFoundExceptionForId(Long memberId) {
		super(ErrorCode.MEMBER_NOT_FOUND,
			"회원을 찾을 수 없습니다. memberId : " + memberId
		);
	}
}
