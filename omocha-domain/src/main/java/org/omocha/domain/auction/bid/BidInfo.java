package org.omocha.domain.auction.bid;

import java.time.LocalDateTime;

public class BidInfo {

	public record BidListResponse(
		Long buyerId,
		Long bidPrice,
		LocalDateTime createdAt
	) {
		public static BidListResponse toResponse(
			Bid bid
		) {
			return new BidListResponse(
				bid.getBuyer().getMemberId(),
				bid.getBidPrice(),
				bid.getCreatedAt()
			);
		}
	}

	public record AddBidResponse(
		Long bidId,
		Long buyerId,
		Long bidPrice,
		LocalDateTime createdAt
	) {
		public static AddBidResponse toDto(
			Bid bid
		) {
			return new AddBidResponse(
				bid.getBidId(),
				bid.getBuyer().getMemberId(),
				bid.getBidPrice(),
				bid.getCreatedAt()
			);
		}
	}

	public record NowPriceResponse(
		Long nowPrice,
		LocalDateTime createdAt,
		LocalDateTime calledAt
	) {
		public static NowPriceResponse toResponse(
			Bid bid
		) {
			return new NowPriceResponse(
				bid.getBidPrice(),
				bid.getCreatedAt(),
				LocalDateTime.now()
			);
		}
	}
}
