package org.auction.client.auction.interfaces.request;

import java.time.LocalDateTime;

import org.auction.domain.auction.domain.enums.AuctionType;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAuctionRequest(
	@NotBlank String title,
	String content,
	// REFACTOR : 나중에 회의 후 VO를 도입할지 어떻게 처리할지 알아봐야함
	@NotNull Integer startPrice,
	@NotNull Integer bidUnit,
	@NotNull AuctionType auctionType,
	@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime startDate,
	@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endDate
) {
}