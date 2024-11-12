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
				.startDate(startDate)
				.endDate(endDate)
				.build();
		}
	}

	public record SearchAuction(
		String title,
		Auction.AuctionStatus auctionStatus,
		Long categoryId
	) {
	}

	public record RetrieveAuction(
		Long auctionId,
		Long memberId
	) {
	}

	public record RemoveAuction(
		Long memberId,
		Long auctionId
	) {
	}

}
