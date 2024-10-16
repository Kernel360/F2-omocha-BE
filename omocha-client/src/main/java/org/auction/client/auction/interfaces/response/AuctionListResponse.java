package org.auction.client.auction.interfaces.response;

import java.time.LocalDateTime;
import java.util.List;

import org.auction.domain.auction.domain.enums.AuctionStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AuctionListResponse(
	Long auctionId,
	String title,
	AuctionStatus status,
	Long startPrice,
	Long nowPrice,
	Long concludePrice,
	Long bidCount,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startDate,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endDate,
	List<String> imageKeys
) {

}
