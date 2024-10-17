package org.auction.client.bid.interfaces.response;

import java.time.LocalDateTime;

import org.auction.domain.bid.entity.BidEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

public record NowPriceResponse(
	Long nowPrice,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {
	public static NowPriceResponse toDto(
		BidEntity bidEntity
	) {
		return new NowPriceResponse(
			bidEntity.getBidPrice(),
			bidEntity.getCreatedAt()
		);
	}
}
