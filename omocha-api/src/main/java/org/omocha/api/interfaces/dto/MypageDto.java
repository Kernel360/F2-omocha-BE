package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.Auction;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MypageDto {

	public record MemberInfoResponse(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		String email
	) {
	}

	public record MypageAuctionListResponse(
		Long auctionId,
		String title,
		Auction.AuctionStatus auctionStatus,
		Long nowPrice, // TODO: 추후 Conclude Price도 넣기
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		List<String> imageKeys
	) {
	}

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

}
