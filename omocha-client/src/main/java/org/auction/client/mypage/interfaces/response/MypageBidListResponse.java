package org.auction.client.mypage.interfaces.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record MypageBidListResponse(
	// TODO : 최종 입찰 , 모든 입찰 내역 논의 후 수정
	Long auctionId,
	String title,
	Long bidPrice,
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime endTime,
	List<String> imageKeys
) {
}
