package org.omocha.core.exception.member;

import org.omocha.client.common.code.MemberCode;

public class MemberNotFoundException extends MemberException {
	public MemberNotFoundException(
		MemberCode memberCode
	) {
		super(memberCode);
	}

	public MemberNotFoundException(
		MemberCode memberCode,
		String detailMessage
	) {
		super(memberCode, detailMessage);
	}
}
