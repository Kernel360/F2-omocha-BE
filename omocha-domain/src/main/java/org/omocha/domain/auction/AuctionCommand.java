package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AuctionCommand {

	public record AddAuction(
		Long memberId,
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		Long instantBuyPrice,
		// TODO : VO 날짜 format 추가
		LocalDateTime startDate,
		LocalDateTime endDate,
		List<MultipartFile> images,
		MultipartFile thumbnailPath,
		List<Long> categoryIds

	) {
		public Auction toEntity() {
			return Auction.builder()
				.memberId(memberId)
				.title(title)
				.content(content)
				.startPrice(startPrice)
				.bidCount(0L)
				.bidUnit(bidUnit)
				.instantBuyPrice(instantBuyPrice)
				.likeCount(0L)
				.startDate(startDate)
				.endDate(endDate)
				.build();
		}
	}

	public record SearchAuction(
		String title,
		Auction.AuctionStatus auctionStatus,
		Long categoryId,
		Long memberId
	) {
	}

	public record RetrieveAuction(
		Long auctionId,
		Long memberId
	) {
	}

	public record RemoveAuction(
		Long auctionId,
		Long memberId
	) {
	}

	public record LikeAuction(
		Long auctionId,
		Long memberId
	) {

	}

}
