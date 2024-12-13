package org.omocha.domain.bid;

import java.time.LocalDateTime;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.member.vo.Email;

import com.querydsl.core.annotations.QueryProjection;

public class BidInfo {

	public record BidList(
		Long buyerMemberId,
		Email buyerEmail,
		String buyerNickname,
		Price bidPrice,
		LocalDateTime createdAt
	) {
		public static BidList toInfo(
			Bid bid
		) {
			return new BidList(
				bid.getBuyer().getMemberId(),
				bid.getBuyer().getEmail(),
				bid.getBuyer().getNickname(),
				bid.getBidPrice(),
				bid.getCreatedAt()
			);
		}
	}

	public record AddBid(
		Long bidId,
		Long buyerMemberId,
		Price bidPrice,
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
		Price nowPrice,
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

		public static NowPrice toInfo(
			BidCacheDto bidCacheDto
		) {
			return new NowPrice(
				bidCacheDto.getPrice(),
				bidCacheDto.getCreatedAt(),
				LocalDateTime.now()
			);

		}
	}

	public record RetrieveMyBids(
		Price bidPrice,
		LocalDateTime createdAt
	) {
		@QueryProjection
		public RetrieveMyBids(
			Price bidPrice,
			LocalDateTime createdAt
		) {
			this.bidPrice = bidPrice;
			this.createdAt = createdAt;
		}
	}

}
