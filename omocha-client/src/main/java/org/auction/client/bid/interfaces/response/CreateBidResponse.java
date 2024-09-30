package org.auction.client.bid.interfaces.response;

import java.time.LocalDateTime;

import org.auction.domain.bid.entity.BidEntity;

public record CreateBidResponse(
	Long buyerId,
	Long bidPrice,
	LocalDateTime createAt
) {
	public static CreateBidResponse toDto(
		BidEntity bidEntity
	) {
		return new CreateBidResponse(
			bidEntity.getMemberEntity().getMemberId(),
			bidEntity.getBidPrice(),
			bidEntity.getCreatedAt()
		);
	}
}
