package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Role;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MypageDto {

	public record CurrentMemberInfoResponse(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		String email,
		String userName,
		String nickName,
		String phoneNumber,
		String birth,
		Role role,
		String profileImageUrl
	) {
	}

	public record MemberModifyRequest(
		String nickName,
		String phoneNumber
	) {
	}

	public record MemberModifyResponse(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		String email,
		String userName,
		String nickName,
		String phoneNumber,
		String birth,
		Role role,
		String profileImageUrl
	) {
	}

	public record PasswordModifyRequest(
		String currentPassword,
		String newPassword
	) {
	}

	public record ProfileImageModifyResponse(
		String imageUrl
	) {

	}

	public record MyAuctionListResponse(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		Long nowPrice, // TODO: 추후 Conclude Price도 넣기
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		String thumbnailPath
	) {
	}

	public record MyBidListResponse(
		Long bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
	}

	public record MyBidAuctionResponse(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath
	) {
	}
}
