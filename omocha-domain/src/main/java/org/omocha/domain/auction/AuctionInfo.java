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
		Long instantBuyPrice,
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
				auction.getInstantBuyPrice(),
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
		Long instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Long nowPrice,
		Long concludePrice,
		Long bidCount,
		boolean isLiked, // 로그인 하지 않은 경우 false
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
			Long instantBuyPrice,
			Auction.AuctionStatus auctionStatus,
			String thumbnailPath,
			Long nowPrice,
			Long concludePrice,
			Long bidCount,
			Boolean isLiked,
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
				instantBuyPrice,
				auctionStatus,
				thumbnailPath,
				nowPrice,
				concludePrice,
				bidCount,
				isLiked,
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
				this.instantBuyPrice,
				this.auctionStatus,
				this.thumbnailPath,
				this.nowPrice,
				this.concludePrice,
				this.bidCount,
				this.isLiked,
				this.startDate,
				this.endDate,
				this.createdAt,
				categoryHierarchy
			);
		}

	}

	public record LikeAuction(
		Long auctionId,
		Long memberId,
		String likeType
	) {
		public static LikeAuction toResponse(
			Long auctionId,
			Long memberId,
			String likeType
		) {
			return new LikeAuction(auctionId, memberId, likeType);
		}
	}

	public record RetrieveMyAuctionLikes(
		Long auctionId,
		String title,
		String thumbnailPath,
		Long startPrice,
		Long nowPrice,
		Auction.AuctionStatus auctionStatus,
		LocalDateTime startDate,
		LocalDateTime endDate,
		LocalDateTime createdAt,
		LocalDateTime likedDate
	) {
		@QueryProjection
		public RetrieveMyAuctionLikes(
			Long auctionId,
			String title,
			String thumbnailPath,
			Long startPrice,
			Long nowPrice,
			Auction.AuctionStatus auctionStatus,
			LocalDateTime startDate,
			LocalDateTime endDate,
			LocalDateTime createdAt,
			LocalDateTime likedDate
		) {
			this.auctionId = auctionId;
			this.title = title;
			this.thumbnailPath = thumbnailPath;
			this.startPrice = startPrice;
			this.nowPrice = nowPrice;
			this.auctionStatus = auctionStatus;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdAt = createdAt;
			this.likedDate = likedDate;
		}
	}

	public record RetrieveMyAuctions(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		Long nowPrice,
		LocalDateTime endDate,
		String thumbnailPath
	) {

		@QueryProjection
		public RetrieveMyAuctions(
			Long auctionId,
			String title,
			Auction.AuctionStatus auctionStatus,
			Long nowPrice,
			LocalDateTime endDate,
			String thumbnailPath
		) {
			this.auctionId = auctionId;
			this.title = title;
			this.auctionStatus = auctionStatus;
			this.nowPrice = nowPrice;
			this.endDate = endDate;
			this.thumbnailPath = thumbnailPath;
		}
	}

	// TODO : 리뷰 추가 이후 리뷰 유무 추가
	public record RetrieveMyBidAuctions(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		String bidStatus
	) {
		@QueryProjection
		public RetrieveMyBidAuctions(
			Long auctionId,
			String title,
			Auction.AuctionStatus auctionStatus,
			String thumbnailPath,
			String bidStatus
		) {
			this.auctionId = auctionId;
			this.title = title;
			this.auctionStatus = auctionStatus;
			this.thumbnailPath = thumbnailPath;
			this.bidStatus = bidStatus;
		}

	}

}
