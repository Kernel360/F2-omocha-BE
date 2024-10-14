package org.auction.domain.bid.entity;

import java.time.LocalDateTime;

import org.auction.domain.auction.domain.entity.AuctionEntity;
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

@Entity(name = "conclude")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcludeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conclude_id")
	private Long concludeId;

	@Column(name = "conclude_price")
	private Long concludePrice;

	@Column(name = "concluded_at")
	private LocalDateTime concludedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private AuctionEntity auctionEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private MemberEntity memberEntity;

	@Builder
	public ConcludeEntity(
		AuctionEntity auctionEntity,
		MemberEntity memberEntity,
		Long concludePrice,
		LocalDateTime concludedAt
	) {
		this.auctionEntity = auctionEntity;
		this.memberEntity = memberEntity;
		this.concludePrice = concludePrice;
		this.concludedAt = concludedAt;
	}
}
