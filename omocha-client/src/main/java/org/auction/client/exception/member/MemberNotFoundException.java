package org.auction.client.exception.member;

import org.auction.client.common.code.MemberCode;

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
