package org.omocha.domain.auction.bid;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

public class BidInfo {

	public record BidList(
		Long buyerId,
		Long bidPrice,
		LocalDateTime createdAt
	) {
		public static BidList toInfo(
			Bid bid
		) {
			return new BidList(
				bid.getBuyer().getMemberId(),
				bid.getBidPrice(),
				bid.getCreatedAt()
			);
		}
	}

	public record AddBid(
		Long bidId,
		Long buyerId,
		Long bidPrice,
		LocalDateTime createdAt
	) {
		public static AddBid toInfo(
			Bid bid
		) {
			return new AddBid(
				bid.getBidId(),
				bid.getBuyer().getMemberId(),
				bid.getBidPrice(),
				bid.getCreatedAt()
			);
		}
	}

	public record NowPrice(
		Long nowPrice,
		LocalDateTime createdAt,
		LocalDateTime calledAt
	) {
		public static NowPrice toInfo(
			Bid bid
		) {
			return new NowPrice(
				bid.getBidPrice(),
				bid.getCreatedAt(),
				LocalDateTime.now()
			);
		}
	}

	public record RetrieveMyBids(
		// TODO : 최종 입찰 , 모든 입찰 내역 논의 후 수정
		Long auctionId,
		String title,
		Long bidPrice,
		LocalDateTime createdAt,
		String thumbnailPath
	) {
		@QueryProjection
		public RetrieveMyBids(
			Long auctionId,
			String title,
			Long bidPrice,
			LocalDateTime createdAt,
			String thumbnailPath
		) {
			this.auctionId = auctionId;
			this.title = title;
			this.bidPrice = bidPrice;
			this.createdAt = createdAt;
			this.thumbnailPath = thumbnailPath;
		}
	}
}
