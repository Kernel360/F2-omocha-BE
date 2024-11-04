package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

public class AuctionInfo {

	public record RetrieveAuction(
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
		LocalDateTime startDate,
		LocalDateTime endDate,
		LocalDateTime createdAt,
		List<String> imagePaths
	) {
		public RetrieveAuction(Auction auction, List<String> imagePaths) {
			this(
				auction.getAuctionId(),
				auction.getMemberId(),
				auction.getTitle(),
				auction.getContent(),
				auction.getStartPrice(),
				auction.getBidUnit(),
				auction.getAuctionStatus(),
				auction.getThumbnailPath(),
				auction.getNowPrice(),
				auction.getBidCount(),
				auction.getStartDate(),
				auction.getEndDate(),
				auction.getCreatedAt(),
				imagePaths
			);
		}
	}

	public record SearchAuction(
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
		LocalDateTime startDate,
		LocalDateTime endDate,
		LocalDateTime createdAt
	) {
		@QueryProjection
		public SearchAuction(
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
			this.nowPrice = nowPrice;
			this.concludePrice = concludePrice;
			this.bidCount = bidCount;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdAt = createdAt;
		}
	}
}
