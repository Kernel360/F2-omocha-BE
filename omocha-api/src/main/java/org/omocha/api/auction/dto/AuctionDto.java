package org.omocha.api.auction.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.category.CategoryInfo;
import org.omocha.domain.member.vo.Email;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuctionDto {

	public record AuctionAddRequest(
		@NotBlank String title,
		@NotBlank String content,
		@NotNull Long startPrice,
		@NotNull Long bidUnit,
		Long instantBuyPrice,
		@NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@NotNull @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@NotNull Long categoryId
	) {
	}

	public record AuctionAddResponse(
		Long auctionId,
		boolean isInstantBuy
	) {
	}

	public record AuctionSearchRequest(
		String title
	) {
	}

	public record AuctionSearchResponse(
		Long auctionId,
		Long memberId,
		Long categoryId,
		String title,
		String content,
		Price startPrice,
		Price bidUnit,
		Price instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		Price concludePrice,
		Long bidCount,
		Long likeCount,
		boolean isLiked,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		List<CategoryInfo.CategoryResponse> categoryResponse
	) {
	}

	public record AuctionDetailsResponse(
		Long auctionId,
		Long memberId,
		Email email,
		String nickname,
		String title,
		String content,
		Price startPrice,
		Price bidUnit,
		Price instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		Long bidCount,
		Long likeCount,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		List<String> imagePaths,
		Long categoryId
	) {
	}

	public record AuctionLikeResponse(
		Long auctionId,
		Long memberId,
		String likeType
	) {
	}

	public record AuctionLikeListResponse(
		Long auctionId,
		Long categoryId,
		String title,
		String thumbnailPath,
		Price startPrice,
		Price nowPrice,
		Price instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime likedDate
	) {
	}

	public record MyAuctionListResponse(
		Long auctionId,
		Long categoryId,
		String title,
		Auction.AuctionStatus auctionStatus,
		Price nowPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		String thumbnailPath,
		boolean reviewStatus
	) {
	}

	public record MemberAuctionListResponse(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		Price nowPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		String thumbnailPath
	) {
	}

	public record MyBidAuctionResponse(
		Long auctionId,
		Long categoryId,
		String title,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		String bidStatus,
		boolean reviewStatus
	) {
	}

}
