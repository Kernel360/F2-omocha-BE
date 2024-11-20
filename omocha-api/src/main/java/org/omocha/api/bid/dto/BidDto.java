package org.omocha.api.bid.dto;

import java.time.LocalDateTime;

import org.omocha.domain.auction.vo.Price;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BidDto {

	public record BidListResponse(
		Long buyerMemberId,
		Price bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {

	}

	public record BidAddRequest(
		Long bidPrice
	) {

	}

	public record BidAddResponse(
		Long buyerMemberId,
		Price bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {

	}

	public record NowPriceResponse(
		Price nowPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime calledAt
	) {

	}

	public record MyBidListResponse(
		Price bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
	}
}
