package org.omocha.core.exception.member;

import org.omocha.client.common.code.MemberCode;

public class InvalidMemberException extends MemberException {
	public InvalidMemberException(
		MemberCode memberCode
	) {
		super(memberCode);
	}

	public InvalidMemberException(
		MemberCode memberCode,
		String detailMessage
	) {
		super(memberCode, detailMessage);
	}
}
