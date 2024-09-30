package org.auction.client.member.interfaces.response;

public record MemberLoginResponse(
	String accessToken,
	String refreshToken
) {
	public static MemberLoginResponse toDto(
		String accessToken, String refreshToken
	) {
		return new MemberLoginResponse(
			accessToken, refreshToken
		);
	}
}
