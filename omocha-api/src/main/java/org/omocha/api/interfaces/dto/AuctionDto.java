package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionDto {

	public record CreateAuctionRequest(
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate
	) {
	}

	public record CreateAuctionResponse(
		Long auctionId
	) {
	}

	public record AuctionSearchCondition(
		String title
	) {
	}

	public record AuctionListResponse(
		Long auctionId,
		// Long memberId
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		// Long nowPrice,
		// Long concludePrice,
		// Long bidCount,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
	}

	public record AuctionDetailResponse(
		Long auctionId,
		// TODO : 추가
		// Long memberId,
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		// Long nowPrice,
		// Long concludePrice,
		// Long bidCount,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		List<String> imagePaths
	) {
	}

}
