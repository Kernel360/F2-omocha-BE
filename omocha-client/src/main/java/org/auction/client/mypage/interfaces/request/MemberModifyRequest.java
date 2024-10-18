package org.auction.client.mypage.interfaces.request;

public record MemberModifyRequest(
	String password,
	String nickName,
	String email,
	String phoneNumber
) {
}
