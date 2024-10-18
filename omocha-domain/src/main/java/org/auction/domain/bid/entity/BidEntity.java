package org.auction.domain.bid.entity;

import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.common.domain.entity.CreateTimeTrackableEntity;
import org.auction.domain.member.domain.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity(name = "bid")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidEntity extends CreateTimeTrackableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bid_id")
	private Long bidId;

	@Column(name = "bid_price")
	private Long bidPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private AuctionEntity auctionEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private MemberEntity buyerEntity;

	@Builder
	public BidEntity(
		AuctionEntity auctionEntity,
		MemberEntity buyerEntity,
		Long bidPrice
	) {
		this.auctionEntity = auctionEntity;
		this.buyerEntity = buyerEntity;
		this.bidPrice = bidPrice;

	}

}
