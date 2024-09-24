package org.auction.client.auction.interfaces.response;

import java.time.LocalDateTime;
import java.util.List;

import org.auction.domain.auction.domain.enums.AuctionType;
import org.springframework.format.annotation.DateTimeFormat;

public record AuctionDetailResponse(
	String title,
	String content,
	Integer startPrice,
	Integer bidUnit,
	AuctionType auctionType,
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startDate,
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endDate,
	List<String> imageKeys // Include image keys
) {
}
