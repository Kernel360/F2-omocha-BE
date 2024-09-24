package org.auction.client.auction.interfaces.request;

import java.time.LocalDateTime;

import org.auction.domain.auction.domain.enums.AuctionType;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAuctionRequest(
	@NotBlank String title,
	String content,
	@NotNull @Min(1) Integer startPrice,
	@NotNull @Min(1) Integer bidUnit,
	@NotNull AuctionType auctionType,
	@NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime startDate,
	@NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime endDate
) {
}