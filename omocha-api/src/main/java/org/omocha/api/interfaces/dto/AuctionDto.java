package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.AuctionStatus;

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
		String title,
		String content,
		AuctionStatus auctionStatus,
		Long startPrice,
		// Long nowPrice,
		// Long concludePrice,
		// Long bidCount,
		Long bidUnit,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		List<String> imagePaths
	) {
	}

	public record AuctionDetailListResponse(
		Long auctionId,
		// TODO : sellerId 추가 예정
		// Long sellerId,
		String title,
		String content,
		AuctionStatus auctionStatus,
		Long startPrice,
		// Long nowPrice,
		// Long concludePrice,
		// Long bidCount,
		Long bidUnit,
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
