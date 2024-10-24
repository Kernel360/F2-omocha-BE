package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionInfo {

	public record Main(
		Long auctionId,
		// TODO : sellerId 추가 예정
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
