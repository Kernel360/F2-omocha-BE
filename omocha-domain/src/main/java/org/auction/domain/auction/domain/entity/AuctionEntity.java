package org.auction.domain.auction.domain.entity;

import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.domain.enums.AuctionType;
import org.auction.domain.common.TimeTrackableEntity;
import org.auction.domain.user.domain.entity.MemberEntity;

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
import lombok.AccessLevel;
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

	// TODO : S3 하면서 변경될 수도 있음
	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "start_price")
	private int startPrice;

	@Column(name = "bid_unit")
	private int bitUnit;

	@Column(name = "view_count")
	private int viewCount;

	@Column(name = "auction_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private AuctionStatus auctionStatus;

	@Column(name = "acution_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private AuctionType auctionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private MemberEntity user;

	// TODO : 삭제 할 때 removed_at 추가 필요
}
