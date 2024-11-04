package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.exception.AuctionAlreadyEndedException;
import org.omocha.domain.exception.AuctionNotInBiddingStateException;
import org.omocha.domain.image.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	private Long startPrice;

	private Long nowPrice;

	private Integer bidCount;

	private Long bidUnit;

	@Enumerated(EnumType.STRING)
	private AuctionStatus auctionStatus;

	private String thumbnailPath;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "auction", fetch = FetchType.LAZY,
		cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	@Builder
	public Auction(
		Long memberId,
		String title,
		String content,
		Long startPrice,
		Long nowPrice,
		Integer bidCount,
		Long bidUnit,
		String thumbnailPath,
		LocalDateTime startDate,
		LocalDateTime endDate
	) {
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.startPrice = startPrice;
		this.nowPrice = nowPrice;
		this.bidCount = bidCount;
		this.bidUnit = bidUnit;
		this.thumbnailPath = thumbnailPath;
		this.auctionStatus = AuctionStatus.BIDDING;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public void updateNowPrice(Long newPrice) {
		this.nowPrice = newPrice;
		this.bidCount += 1;
	}

	public void validateAuctionStatus() {
		LocalDateTime now = LocalDateTime.now();

		if (getEndDate().isBefore(now)) {
			throw new AuctionAlreadyEndedException(auctionId);
		}

		if (getAuctionStatus() != AuctionStatus.BIDDING) {
			throw new AuctionNotInBiddingStateException(auctionId, auctionStatus);
		}
	}

	public void statusConcluded() {
		this.auctionStatus = AuctionStatus.CONCLUDED;
	}

	public void statusNoBids() {
		this.auctionStatus = AuctionStatus.NO_BIDS;
	}
}

