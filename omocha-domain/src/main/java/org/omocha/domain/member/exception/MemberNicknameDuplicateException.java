package org.omocha.domain.member.exception;

import org.omocha.domain.common.code.ErrorCode;

public class MemberNicknameDuplicateException extends MemberException {
	public MemberNicknameDuplicateException(String nickname) {
		super(
			ErrorCode.NICKNAME_DUPLICATE,
			"닉네임이 이미 사용 중입니다. " + ", nickname : " + nickname

		);
	}
}
