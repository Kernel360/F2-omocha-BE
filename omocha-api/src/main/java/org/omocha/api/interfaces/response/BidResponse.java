package org.omocha.api.interfaces.response;

import java.time.LocalDateTime;

import org.omocha.domain.auction.bid.BidEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BidResponse(
	Long buyerId,
	Long bidPrice,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static BidResponse toDto(
		BidEntity bidEntity
	) {
		return new BidResponse(
			bidEntity.getBuyerEntity().getMemberId(),
			bidEntity.getBidPrice(),
			bidEntity.getCreatedAt()
		);
	}

}
