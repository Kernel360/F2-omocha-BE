package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;

public class AuctionInfo {

	public record AuctionDetailResponse(
		Long auctionId,
		Long memberId,
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
		public AuctionDetailResponse(Auction auction, List<String> imagePaths) {
			this(
				auction.getAuctionId(),
				auction.getMemberId(),
				auction.getTitle(),
				auction.getContent(),
				auction.getStartPrice(),
				auction.getBidUnit(),
				auction.getAuctionStatus(),
				auction.getThumbnailPath(),
				// auction.getNowPrice(), // 예시 메서드
				// auction.getConcludePrice(), // 예시 메서드
				// auction.getBidCount(), // 예시 메서드
				auction.getStartDate(),
				auction.getEndDate(),
				auction.getCreatedAt(),
				imagePaths
			);
		}
	}

	public record AuctionListResponse(
		Long auctionId,
		Long memberId,
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
		@QueryProjection
		public AuctionListResponse(
			Long auctionId,
			Long memberId,
			String title,
			String content,
			Long startPrice,
			Long bidUnit,
			Auction.AuctionStatus auctionStatus,
			String thumbnailPath,
			// Long nowPrice,
			// Long concludePrice,
			// Long bidCount,
			LocalDateTime startDate,
			LocalDateTime endDate,
			LocalDateTime createdAt
		) {
			this.auctionId = auctionId;
			this.memberId = memberId;
			this.title = title;
			this.content = content;
			this.startPrice = startPrice;
			this.bidUnit = bidUnit;
			this.auctionStatus = auctionStatus;
			this.thumbnailPath = thumbnailPath;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdAt = createdAt;
		}
	}
}
