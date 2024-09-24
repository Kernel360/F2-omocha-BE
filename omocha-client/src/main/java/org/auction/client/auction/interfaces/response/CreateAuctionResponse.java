package org.auction.client.auction.interfaces.response;

import org.auction.domain.auction.domain.entity.AuctionEntity;

public record CreateAuctionResponse(
	Long auctionId
) {
	public static CreateAuctionResponse toDto(
		AuctionEntity auctionEntity
	) {
		return new CreateAuctionResponse(auctionEntity.getAuctionId());
	}
}
