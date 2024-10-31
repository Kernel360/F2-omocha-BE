// package org.omocha.domain.member.mypage;
//
// import java.time.LocalDateTime;
// import java.util.List;
//
// import org.omocha.domain.auction.AuctionStatus;
//
// import com.fasterxml.jackson.annotation.JsonFormat;
//
// public class MypageInfo {
//
// 	public record MypageAuctionListInfo(
// 		Long auctionId,
// 		String title,
// 		AuctionStatus auctionStatus,
// 		Long nowPrice, // TODO: 추후 Conclude Price도 넣기
// 		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
// 		LocalDateTime endDate,
// 		List<String> imageKeys
// 	) {
//
// 	}
//
// 	public record MypageBidListInfo(
// 		// TODO : 최종 입찰 , 모든 입찰 내역 논의 후 수정
// 		Long auctionId,
// 		String title,
// 		Long bidPrice,
// 		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
// 		LocalDateTime endTime,
// 		List<String> imageKeys
// 	) {
// 	}
//
// }
