package org.auction.client.auction.interfaces.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AuctionListResponse(
	Long auctionId,
	String title,
	Long startPrice,
	Long nowPrice,
	Long bidCount,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startDate,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endDate,
	List<String> imageKeys
) {

}
