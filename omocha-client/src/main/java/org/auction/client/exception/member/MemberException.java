package org.auction.client.exception.member;

import org.auction.client.common.code.MemberCode;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

	private final MemberCode memberCode;
	private final String detailMessage;

	public MemberException(
		MemberCode memberCode
	) {
		super(memberCode.getResultMsg());
		this.memberCode = memberCode;
		this.detailMessage = memberCode.getResultMsg();
	}

	public MemberException(
		MemberCode memberCode,
		String detailMessage
	) {
		super(memberCode.getResultMsg());
		this.memberCode = memberCode;
		this.detailMessage = detailMessage;
	}

}
