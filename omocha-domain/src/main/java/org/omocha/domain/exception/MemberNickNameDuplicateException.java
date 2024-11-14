package org.omocha.domain.exception;

import org.omocha.domain.exception.code.ErrorCode;

public class MemberNickNameDuplicateException extends MemberException {
	public MemberNickNameDuplicateException(String nickName) {
		super(
			ErrorCode.NICKNAME_DUPLICATE,
			"닉네임이 이미 사용 중입니다. " + ", nickName : " + nickName

		);
	}
}
