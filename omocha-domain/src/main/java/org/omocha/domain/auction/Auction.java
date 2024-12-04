package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.omocha.domain.auction.exception.AuctionAlreadyEndedException;
import org.omocha.domain.auction.exception.AuctionNotConcludedException;
import org.omocha.domain.auction.exception.AuctionNotInBiddingStateException;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.auction.vo.PriceDbConverter;
import org.omocha.domain.category.Category;
import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.conclude.Conclude;
import org.omocha.domain.image.Image;
import org.omocha.domain.likes.exception.LikeCountNegativeException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auction")
public class Auction extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long auctionId;

	private Long memberId;

	private String title;

	private String content;

	@Convert(converter = PriceDbConverter.class)
	private Price startPrice;

	@Convert(converter = PriceDbConverter.class)
	private Price nowPrice;

	private Long bidCount;

	@Convert(converter = PriceDbConverter.class)
	private Price bidUnit;

	private long likeCount;

	@Convert(converter = PriceDbConverter.class)
	private Price instantBuyPrice;

	@Enumerated(EnumType.STRING)
	private AuctionStatus auctionStatus;

	private String thumbnailPath;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@BatchSize(size = 10)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "auction_id")
	private List<Image> images = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToOne(mappedBy = "auction")
	private Conclude conclude;

	@Builder
	public Auction(
		Long memberId,
		String title,
		String content,
		Price startPrice,
		Price nowPrice,
		Long bidCount,
		Price bidUnit,
		Price instantBuyPrice,
		long likeCount,
		String thumbnailPath,
		LocalDateTime startDate,
		LocalDateTime endDate,
		Category category
	) {
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.startPrice = startPrice;
		this.nowPrice = nowPrice;
		this.bidCount = bidCount;
		this.bidUnit = bidUnit;
		this.likeCount = likeCount;
		this.instantBuyPrice = instantBuyPrice;
		this.thumbnailPath = thumbnailPath;
		this.auctionStatus = AuctionStatus.BIDDING;
		this.startDate = startDate;
		this.endDate = endDate;
		this.category = category;
	}

	@Getter
	@RequiredArgsConstructor
	public enum AuctionStatus {
		PREBID("PREBID"),
		BIDDING("BIDDING"),
		NO_BIDS("NO_BIDS"),
		CONCLUDED("CONCLUDED"),
		COMPLETED("COMPLETED");

		private final String description;
	}

	public void thumbnailPathUpload(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public void updateNowPrice(Price newPrice) {
		this.nowPrice = newPrice;
		this.bidCount += 1;
	}

	public void validateAuctionStatus() {
		if (getAuctionStatus() != AuctionStatus.BIDDING) {
			throw new AuctionNotInBiddingStateException(auctionId, auctionStatus);
		}

		LocalDateTime now = LocalDateTime.now();
		if (getEndDate().isBefore(now)) {
			throw new AuctionAlreadyEndedException(auctionId);
		}
	}

	public void statusConcluded() {
		this.auctionStatus = AuctionStatus.CONCLUDED;
	}

	public void statusNoBids() {
		this.auctionStatus = AuctionStatus.NO_BIDS;
	}

	public void validateAuctionStatusConcludedOrCompleted() {
		if (!(auctionStatus.equals(AuctionStatus.CONCLUDED) || auctionStatus.equals(AuctionStatus.COMPLETED))) {
			throw new AuctionNotConcludedException(auctionId, auctionStatus);
		}
	}

	public void increaseLikeCount() {
		likeCount++;
	}

	public void decreaseLikeCount() {
		if (likeCount < 0) {
			throw new LikeCountNegativeException();
		}
		likeCount--;
	}

}

