package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionDto {

	public record AuctionAddRequest(
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
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Long nowPrice,
		Long concludePrice,
		Long bidCount,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
	}

	public record AuctionDetailsResponse(
		Long auctionId,
		Long memberId,
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Long nowPrice,
		Long bidCount,
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
