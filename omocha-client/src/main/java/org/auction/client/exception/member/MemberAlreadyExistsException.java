package org.auction.client.exception.member;

import org.auction.client.common.code.MemberCode;

public class MemberAlreadyExistsException extends MemberException {
	public MemberAlreadyExistsException(
		MemberCode memberCode
	) {
		super(memberCode);
	}

	public MemberAlreadyExistsException(
		MemberCode memberCode,
		String detailMessage
	) {
		super(memberCode, detailMessage);
	}
}
