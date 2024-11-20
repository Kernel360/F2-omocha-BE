package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.member.vo.Email;

public class MemberNotFoundExceptionForEmail extends MemberException {
	public MemberNotFoundExceptionForEmail(Email email) {
		super(ErrorCode.MEMBER_NOT_FOUND,
			"회원을 찾을 수 없습니다. email : " + email
		);
	}
}
