package org.omocha.domain.auction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.omocha.domain.common.BaseEntity;
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

	private String title;

	private String content;

	private Long startPrice;

	private Long bidUnit;

	@Enumerated(EnumType.STRING)
	private AuctionStatus auctionStatus;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "auction", fetch = FetchType.LAZY,
		cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images = new ArrayList<>();

	// TODO: ImageEntity 연관관계 추가
	// TODO: MemberID 추가

	@Builder
	public Auction(
		String title,
		String content,
		Long startPrice,
		Long bidUnit,
		LocalDateTime startDate,
		LocalDateTime endDate
	) {

		this.title = title;
		this.content = content;
		this.startPrice = startPrice;
		this.bidUnit = bidUnit;
		this.auctionStatus = AuctionStatus.BIDDING;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}

