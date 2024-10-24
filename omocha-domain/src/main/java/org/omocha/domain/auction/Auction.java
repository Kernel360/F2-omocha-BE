package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.omocha.domain.image.Image;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Auction {

	private Long auctionId;

	private String title;

	private String content;

	private Long startPrice;

	private Long bidUnit;

	private AuctionStatus auctionStatus;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private List<Image> images = new ArrayList<>();

	// TODO: ImageEntity 연관관계 추가
	// TODO: MemberID 추가

	@Builder
	public Auction(String title, String content, Long startPrice, Long bidUnit, LocalDateTime startDate,
		LocalDateTime endDate) {

		this.title = title;
		this.content = content;
		this.startPrice = startPrice;
		this.bidUnit = bidUnit;
		this.auctionStatus = AuctionStatus.BIDDING;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}

