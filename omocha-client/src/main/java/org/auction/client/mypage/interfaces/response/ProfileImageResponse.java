package org.auction.client.mypage.interfaces.response;

public record ProfileImageResponse(
	String imageUrl
) {
	public static ProfileImageResponse toDto(
		String profileImage
	) {
		return new ProfileImageResponse(
			profileImage
		);
	}

}
