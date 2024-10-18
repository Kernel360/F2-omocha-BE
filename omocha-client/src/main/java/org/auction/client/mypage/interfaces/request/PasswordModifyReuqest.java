package org.auction.client.mypage.interfaces.request;

public record PasswordModifyReuqest(
	String oldPassword,
	String newPassword
) {
}
