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
		List<String> imagePaths,
		List<CategoryInfo.CategoryResponse> categories
	) {
		public RetrieveAuction(Auction auction, List<String> imagePaths,
			List<CategoryInfo.CategoryResponse> categories) {
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
				imagePaths,
				categories
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
		LocalDateTime createdAt,
		List<CategoryInfo.CategoryResponse> categoryResponse
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
			this(
				auctionId,
				memberId,
				title,
				content,
				startPrice,
				bidUnit,
				auctionStatus,
				thumbnailPath,
				nowPrice,
				concludePrice,
				bidCount,
				startDate,
				endDate,
				createdAt,
				null
			);
		}

		public SearchAuction withCategoryHierarchy(List<CategoryInfo.CategoryResponse> categoryHierarchy) {
			return new SearchAuction(
				this.auctionId,
				this.memberId,
				this.title,
				this.content,
				this.startPrice,
				this.bidUnit,
				this.auctionStatus,
				this.thumbnailPath,
				this.nowPrice,
				this.concludePrice,
				this.bidCount,
				this.startDate,
				this.endDate,
				this.createdAt,
				categoryHierarchy
			);
		}
	}

}
