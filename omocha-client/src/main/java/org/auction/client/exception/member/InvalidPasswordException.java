package org.auction.client.exception.member;

import org.auction.client.common.code.MemberCode;

public class InvalidPasswordException extends MemberException {
	public InvalidPasswordException(MemberCode memberCode) {
		super(memberCode);
	}

	public InvalidPasswordException(MemberCode memberCode, String detailMessage) {
		super(memberCode, detailMessage);
	}
}
