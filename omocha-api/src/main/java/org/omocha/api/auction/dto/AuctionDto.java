package org.omocha.api.auction.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.category.CategoryInfo;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionDto {

	public record AuctionAddRequest(
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Long instantBuyPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		List<Long> categoryIds
	) {
	}

	public record AuctionAddResponse(
		Long auctionId
	) {
	}

	public record AuctionSearchRequest(
		String title
	) {
	}

	public record AuctionSearchResponse(
		Long auctionId,
		Long memberId,
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Long instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Long nowPrice,
		Long concludePrice,
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
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Long instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Long nowPrice,
		Long bidCount,
		Long likeCount,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		List<String> imagePaths,
		List<CategoryInfo.CategoryResponse> categories
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
		String title,
		String thumbnailPath,
		Long startPrice,
		Long nowPrice,
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
		String title,
		Auction.AuctionStatus auctionStatus,
		Long nowPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		String thumbnailPath,
		boolean reviewStatus
	) {
	}

	public record MyBidAuctionResponse(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		String bidStatus,
		boolean reviewStatus
	) {
	}

}
