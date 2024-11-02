package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionCommand {

	public record RegisterAuction(
		Long memberId,
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime startDate,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime endDate,
		List<MultipartFile> images,
		MultipartFile thumbnailPath

	) {
		public Auction toEntity() {
			return Auction.builder()
				.memberId(memberId)
				.title(title)
				.content(content)
				.startPrice(startPrice)
				.bidCount(0)
				.bidUnit(bidUnit)
				.startDate(startDate)
				.endDate(endDate)
				.build();
		}
	}

	public record SearchAuction(
		String title,
		Auction.AuctionStatus auctionStatus
	) {
	}

	public record RetrieveAuction(
		Long auctionId
	) {
	}

}
