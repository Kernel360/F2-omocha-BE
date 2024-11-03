package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BidDto {

	public record BidListResponse(
		Long buyerId,
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
		Long buyerId,
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
}
