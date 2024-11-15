package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MemberAlreadyExistException extends MemberException {
	public MemberAlreadyExistException(String email) {
		super(
			ErrorCode.MEMBER_ALREADY_EXISTS,
			"이미 존재하는 회원입니다. memberId : " + email

		);
	}
}
