package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.category.Category;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

public class AuctionCommand {

	@Builder
	public record AddAuction(
		Long memberId,
		String title,
		String content,
		Price startPrice,
		Price bidUnit,
		Price instantBuyPrice,
		// TODO : VO 날짜 format 추가
		LocalDateTime startDate,
		LocalDateTime endDate,
		List<MultipartFile> images,
		Long categoryId

	) {
		public Auction toEntity(Category category) {
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
				.category(category)
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
		Long auctionId
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

	public record RetrieveMyAuctions(
		Long memberId,
		Auction.AuctionStatus auctionStatus
	) {
	}

	public record RetrieveMemberAuctions(
		Long memberId,
		Auction.AuctionStatus auctionStatus
	) {
	}

	public record RetrieveMyBidAuctions(
		Long memberId
	) {
	}

}
