package org.auction.domain.auction.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.domain.enums.AuctionType;
import org.auction.domain.common.domain.entity.TimeTrackableEntity;
import org.auction.domain.image.domain.entity.ImageEntity;
import org.auction.domain.user.domain.entity.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "auction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionEntity extends TimeTrackableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auction_id")
	private Long auctionId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "start_price")
	private int startPrice;

	@Column(name = "bid_unit")
	private int bidUnit;

	// TODO: 조회수, 찜 추가 필요
	/*@Column(name = "view_count")
	private int viewCount;*/

	@Column(name = "auction_status")
	@Enumerated(EnumType.STRING)
	private AuctionStatus auctionStatus;

	@Column(name = "auction_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AuctionType auctionType;

	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDateTime endDate;

	@OneToMany(mappedBy = "auctionEntity", fetch = FetchType.LAZY,
		cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ImageEntity> images = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private MemberEntity memberEntity;

	// TODO : 삭제 할 때 removed_at 추가 필요

	@Builder
	public AuctionEntity(
		String title, String content, int startPrice, int bidUnit, AuctionStatus auctionStatus,
		AuctionType auctionType, LocalDateTime startDate, LocalDateTime endDate,
		MemberEntity memberEntity
	) {
		this.title = title;
		this.content = content;
		this.startPrice = startPrice;
		this.bidUnit = bidUnit;
		this.auctionType = auctionType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.memberEntity = memberEntity;
	}

	public void addImage(
		ImageEntity image
	) {
		images.add(image);
		image.setAuctionEntity(this);
	}

}
