package org.omocha.domain.auction.bid;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.exception.BidBelowStartPriceException;
import org.omocha.domain.exception.BidExceedsInstantBuyException;
import org.omocha.domain.exception.BidNotExceedingCurrentHighestException;
import org.omocha.domain.exception.InvalidBidUnitException;
import org.omocha.domain.exception.SelfBidNotAllowedException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BidValidatorImpl implements BidValidator {

	private final BidReader bidReader;

	// EXPLAIN : 가격 검증 메서드
	// 처음 입찰 : 시작가보다 낮은 입찰 가격이 발생할 때 예외
	// 이후 입찰 : 입찰 가격 < 현재 최고가 보다 낮을 때 예외 발생
	@Override
	public void bidValidate(Auction auction, Long buyerId, Long bidPrice) {
		validateSelfBid(auction, buyerId);

		validateBidUnit(auction, bidPrice);

		validateBidAboveStartPrice(auction, bidPrice);

		validateBidExceedsCurrentHighest(auction, bidPrice);

		validateBidBelowInstantBuyPrice(auction, bidPrice);
	}

	private void validateSelfBid(Auction auction, Long buyerId) {
		if (auction.getMemberId().equals(buyerId)) {
			throw new SelfBidNotAllowedException(auction.getAuctionId(), buyerId);
		}
	}

	private void validateBidUnit(Auction auction, Long bidPrice) {
		Long startPrice = auction.getStartPrice();
		Long bidUnit = auction.getBidUnit();

		if ((bidPrice - startPrice) % bidUnit != 0) {
			throw new InvalidBidUnitException(startPrice, bidUnit);
		}
	}

	private void validateBidAboveStartPrice(Auction auction, Long bidPrice) {
		Long startPrice = auction.getStartPrice();

		if (bidPrice < startPrice) {
			throw new BidBelowStartPriceException(bidPrice, startPrice);
		}
	}

	private void validateBidExceedsCurrentHighest(Auction auction, Long bidPrice) {
		Long currentHighestBidPrice = bidReader.findHighestBid(auction.getAuctionId())
			.map(Bid::getBidPrice)
			.orElse(0L);

		if (bidPrice <= currentHighestBidPrice) {
			throw new BidNotExceedingCurrentHighestException(bidPrice, currentHighestBidPrice);
		}
	}

	private void validateBidBelowInstantBuyPrice(Auction auction, Long bidPrice) {
		Long instantBuyPrice = auction.getInstantBuyPrice();

		if (bidPrice >= instantBuyPrice) {
			throw new BidExceedsInstantBuyException(bidPrice, instantBuyPrice);
		}
	}

	@Override
	public void instantBuyValidate(Auction auction, Long buyerId) {
		validateSelfBid(auction, buyerId);
	}
}
