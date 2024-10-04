package org.auction.client.exception.member;

import org.auction.client.common.code.MemberCode;

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
