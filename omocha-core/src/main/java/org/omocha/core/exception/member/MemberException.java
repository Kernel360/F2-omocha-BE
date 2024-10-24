package org.omocha.core.exception.member;

import org.omocha.client.common.code.MemberCode;

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
