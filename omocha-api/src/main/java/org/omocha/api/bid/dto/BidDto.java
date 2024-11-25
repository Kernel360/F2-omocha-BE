package org.omocha.api.bid.dto;

import java.time.LocalDateTime;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.member.vo.Email;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public class BidDto {

	public record BidListResponse(
		Long buyerMemberId,
		Email buyerEmail,
		String buyerNickname,
		Price bidPrice,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createdAt
	) {

	}

	public record BidAddRequest(
		@NotNull Long bidPrice
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
