package org.omocha.api.interfaces.response;

import org.omocha.domain.auction.AuctionEntity;

public record CreateAuctionResponse(
	Long auctionId
) {
	public static CreateAuctionResponse toDto(
		AuctionEntity auctionEntity
	) {
		return new CreateAuctionResponse(auctionEntity.getAuctionId());
	}
}
