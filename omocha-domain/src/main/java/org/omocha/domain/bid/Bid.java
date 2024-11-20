package org.omocha.domain.bid;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.auction.vo.PriceDbConverter;
import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.member.Member;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "bid")
public class Bid extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bidId;

	@Convert(converter = PriceDbConverter.class)
	private Price bidPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private Auction auction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_member_id")
	private Member buyer;

	@Builder
	public Bid(
		Auction auction,
		Member buyer,
		Price bidPrice
	) {
		this.auction = auction;
		this.buyer = buyer;
		this.bidPrice = bidPrice;
	}
}