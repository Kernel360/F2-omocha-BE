package org.auction.client.exception.member;

import org.auction.client.common.code.MemberCode;

public class MemberEmailAlreadyExistsException extends MemberException {
	public MemberEmailAlreadyExistsException(
		MemberCode memberCode
	) {
		super(memberCode);
	}

	public MemberEmailAlreadyExistsException(
		MemberCode memberCode,
		String detailMessage
	) {
		super(memberCode, detailMessage);
	}
}
