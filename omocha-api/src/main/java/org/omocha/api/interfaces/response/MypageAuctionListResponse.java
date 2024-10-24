package org.omocha.api.interfaces.response;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.AuctionStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public record MypageAuctionListResponse(
	Long auctionId,
	String title,
	AuctionStatus auctionStatus,
	Long nowPrice, // TODO: 추후 Conclude Price도 넣기
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endDate,
	List<String> imageKeys
) {

}
