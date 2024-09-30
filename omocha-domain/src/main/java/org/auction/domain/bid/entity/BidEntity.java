package org.auction.domain.bid.entity;

import java.time.LocalDateTime;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.user.domain.entity.MemberEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "bids")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BidEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bid_id")
	private Long bidId;

	@Column(name = "bid_price")
	private Long bidPrice;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "auction_id")
	private AuctionEntity auctionEntity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id")
	private MemberEntity memberEntity;

	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public BidEntity(AuctionEntity auctionEntity, MemberEntity memberEntity, Long bidPrice) {
		this.auctionEntity = auctionEntity;
		this.memberEntity = memberEntity;
		this.bidPrice = bidPrice;

	}

}
