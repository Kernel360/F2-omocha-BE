package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MemberNickNameDuplicateException extends MemberException {
	public MemberNickNameDuplicateException(String nickName) {
		super(
			ErrorCode.NICKNAME_DUPLICATE,
			"닉네임이 이미 사용 중입니다. " + ", nickName : " + nickName

		);
	}
}
