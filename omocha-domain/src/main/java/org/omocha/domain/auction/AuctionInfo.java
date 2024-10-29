package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionInfo {

	public record AuctionDetailResponse(
		Long auctionId,
		// TODO : seller ID 추가 해야함 Member 완성되면
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
}
