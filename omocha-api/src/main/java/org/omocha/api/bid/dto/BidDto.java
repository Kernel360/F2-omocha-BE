package org.omocha.api.bid.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BidDto {

	public record BidListResponse(
		Long buyerMemberId,
		Long bidPrice,
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
		Long bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {

	}

	public record NowPriceResponse(
		Long nowPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime calledAt
	) {

	}

	public record MyBidListResponse(
		Long bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {
	}
}
