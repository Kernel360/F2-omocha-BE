package org.auction.client.bid.interfaces.response;

import java.time.LocalDateTime;

import org.auction.domain.bid.entity.BidEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CreateBidResponse(
	Long buyerId,
	Long bidPrice,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static CreateBidResponse toDto(
		BidEntity bidEntity
	) {
		return new CreateBidResponse(
			bidEntity.getBuyerEntity().getMemberId(),
			bidEntity.getBidPrice(),
			bidEntity.getCreatedAt()
		);
	}
}
