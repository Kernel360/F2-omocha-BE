package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MemberIdenticalPassword extends MemberException {
	public MemberIdenticalPassword(Long memberId) {
		super(ErrorCode.IDENTICAL_PASSWORD,
			"입력한 새 비밀번호가 기존 비밀번호와 동일합니다. memberId=" + memberId
		);
	}
}
