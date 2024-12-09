package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.category.CategoryInfo;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.vo.Email;

import com.querydsl.core.annotations.QueryProjection;

public class AuctionInfo {

	public record AddAuction(
		Long auctionId,
		boolean isInstantBuy
	) {
		public static AuctionInfo.AddAuction toInfo(
			Auction auction
		) {
			return new AuctionInfo.AddAuction(
				auction.getAuctionId(),
				(auction.getInstantBuyPrice() != null)
			);
		}
	}

	public record RetrieveAuction(
		Long auctionId,
		Long memberId,
		Email email,
		String nickname,
		String title,
		String content,
		Price startPrice,
		Price bidUnit,
		Price instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		Long bidCount,
		Long likeCount,
		LocalDateTime startDate,
		LocalDateTime endDate,
		LocalDateTime createdAt,
		List<String> imagePaths,
		Long categoryId
	) {
		public RetrieveAuction(
			Auction auction,
			Member member,
			List<String> imagePaths,
			Long categoryId
		) {
			this(
				auction.getAuctionId(),
				member.getMemberId(),
				member.getEmail(),
				member.getNickname(),
				auction.getTitle(),
				auction.getContent(),
				auction.getStartPrice(),
				auction.getBidUnit(),
				auction.getInstantBuyPrice(),
				auction.getAuctionStatus(),
				auction.getThumbnailPath(),
				auction.getNowPrice(),
				auction.getBidCount(),
				auction.getLikeCount(),
				auction.getStartDate(),
				auction.getEndDate(),
				auction.getCreatedAt(),
				imagePaths,
				auction.getCategory().getCategoryId()
			);
		}
	}

	public record SearchAuction(
		Long auctionId,
		Long memberId,
		Long categoryId,
		String title,
		String content,
		Price startPrice,
		Price bidUnit,
		Price instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		Price concludePrice,
		Long bidCount,
		Long likeCount,
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
			Long categoryId,
			String title,
			String content,
			Price startPrice,
			Price bidUnit,
			Price instantBuyPrice,
			Auction.AuctionStatus auctionStatus,
			String thumbnailPath,
			Price nowPrice,
			Price concludePrice,
			Long bidCount,
			Long likeCount,
			Boolean isLiked,
			LocalDateTime startDate,
			LocalDateTime endDate,
			LocalDateTime createdAt
		) {
			this(
				auctionId,
				memberId,
				categoryId,
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
				likeCount,
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
				this.categoryId,
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
				this.likeCount,
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
		Long categoryId,
		String title,
		String thumbnailPath,
		Price startPrice,
		Price nowPrice,
		Price instantBuyPrice,
		Auction.AuctionStatus auctionStatus,
		LocalDateTime startDate,
		LocalDateTime endDate,
		LocalDateTime createdAt,
		LocalDateTime likedDate
	) {
		@QueryProjection
		public RetrieveMyAuctionLikes(
			Long auctionId,
			Long categoryId,
			String title,
			String thumbnailPath,
			Price startPrice,
			Price nowPrice,
			Price instantBuyPrice,
			Auction.AuctionStatus auctionStatus,
			LocalDateTime startDate,
			LocalDateTime endDate,
			LocalDateTime createdAt,
			LocalDateTime likedDate
		) {
			this.auctionId = auctionId;
			this.categoryId = categoryId;
			this.title = title;
			this.thumbnailPath = thumbnailPath;
			this.startPrice = startPrice;
			this.nowPrice = nowPrice;
			this.instantBuyPrice = instantBuyPrice;
			this.auctionStatus = auctionStatus;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdAt = createdAt;
			this.likedDate = likedDate;
		}
	}

	public record RetrieveMyAuctions(
		Long auctionId,
		Long categoryId,
		String title,
		Auction.AuctionStatus auctionStatus,
		Price nowPrice,
		LocalDateTime endDate,
		String thumbnailPath,
		boolean reviewStatus
	) {

		@QueryProjection
		public RetrieveMyAuctions(
			Long auctionId,
			Long categoryId,
			String title,
			Auction.AuctionStatus auctionStatus,
			Price nowPrice,
			LocalDateTime endDate,
			String thumbnailPath,
			boolean reviewStatus
		) {
			this.auctionId = auctionId;
			this.categoryId = categoryId;
			this.title = title;
			this.auctionStatus = auctionStatus;
			this.nowPrice = nowPrice;
			this.endDate = endDate;
			this.thumbnailPath = thumbnailPath;
			this.reviewStatus = reviewStatus;
		}
	}

	public record RetrieveMemberAuctions(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		LocalDateTime endDate
	) {

		@QueryProjection
		public RetrieveMemberAuctions(
			Long auctionId,
			String title,
			Auction.AuctionStatus auctionStatus,
			String thumbnailPath,
			Price nowPrice,
			LocalDateTime endDate
		) {
			this.auctionId = auctionId;
			this.title = title;
			this.auctionStatus = auctionStatus;
			this.nowPrice = nowPrice;
			this.thumbnailPath = thumbnailPath;
			this.endDate = endDate;
		}
	}

	// TODO : 리뷰 추가 이후 리뷰 유무 추가
	public record RetrieveMyBidAuctions(
		Long auctionId,
		Long categoryId,
		String title,
		Auction.AuctionStatus auctionStatus,
		String thumbnailPath,
		Price nowPrice,
		LocalDateTime endDate,
		String bidStatus,
		boolean reviewStatus
	) {
		@QueryProjection
		public RetrieveMyBidAuctions(
			Long auctionId,
			Long categoryId,
			String title,
			Auction.AuctionStatus auctionStatus,
			String thumbnailPath,
			Price nowPrice,
			LocalDateTime endDate,
			String bidStatus,
			boolean reviewStatus
		) {
			this.auctionId = auctionId;
			this.categoryId = categoryId;
			this.title = title;
			this.auctionStatus = auctionStatus;
			this.thumbnailPath = thumbnailPath;
			this.nowPrice = nowPrice;
			this.endDate = endDate;
			this.bidStatus = bidStatus;
			this.reviewStatus = reviewStatus;
		}

	}

}
