package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.member.vo.Email;

public class MemberAlreadyExistException extends MemberException {
	public MemberAlreadyExistException(Email email) {
		super(
			ErrorCode.MEMBER_ALREADY_EXISTS,
			"이미 존재하는 회원입니다. email: " + email.getValue()
		);
	}
}
