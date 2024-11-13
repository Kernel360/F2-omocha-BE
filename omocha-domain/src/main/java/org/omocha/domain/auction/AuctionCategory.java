package org.omocha.domain.auction;

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

@Getter
@Entity
@Table(name = "auction_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long auctionCategoryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private Auction auction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Builder
	public AuctionCategory(Auction auction, Category category) {
		this.auction = auction;
		this.category = category;
	}

}
