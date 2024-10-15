package org.auction.client.auction.interfaces.response;

import java.time.LocalDateTime;
import java.util.List;

import org.auction.domain.auction.domain.enums.AuctionType;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AuctionDetailResponse(
	String title,
	String content,
	Long startPrice,
	Long nowPrice, // TODO: 추후 Conclude Price도 넣기
	Long bidCount,
	Integer bidUnit,
	AuctionType auctionType,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startDate,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endDate,
	List<String> imageKeys // Include image keys
) {
}
