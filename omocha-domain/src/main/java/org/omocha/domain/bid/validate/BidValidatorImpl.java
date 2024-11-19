package org.omocha.domain.bid.validate;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidReader;
import org.omocha.domain.bid.exception.BidBelowStartPriceException;
import org.omocha.domain.bid.exception.BidExceedsInstantBuyException;
import org.omocha.domain.bid.exception.BidNotExceedingCurrentHighestException;
import org.omocha.domain.bid.exception.InvalidBidUnitException;
import org.omocha.domain.bid.exception.SelfBidNotAllowedException;
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
	public void bidValidate(Auction auction, Long buyerMemberId, Price bidPrice) {
		validateSelfBid(auction, buyerMemberId);

		validateBidUnit(auction, bidPrice);

		validateBidAboveStartPrice(auction, bidPrice);

		validateBidExceedsCurrentHighest(auction, bidPrice);

		validateBidBelowInstantBuyPrice(auction, bidPrice);
	}

	private void validateSelfBid(Auction auction, Long buyerMemberId) {
		if (auction.getMemberId().equals(buyerMemberId)) {
			throw new SelfBidNotAllowedException(auction.getAuctionId(), buyerMemberId);
		}
	}

	private void validateBidUnit(Auction auction, Price bidPrice) {
		Price startPrice = auction.getStartPrice();
		Price bidUnit = auction.getBidUnit();

		if ((bidPrice.getValue() - startPrice.getValue()) % bidUnit.getValue() != 0) {
			throw new InvalidBidUnitException(startPrice, bidUnit);
		}
	}

	private void validateBidAboveStartPrice(Auction auction, Price bidPrice) {
		Price startPrice = auction.getStartPrice();

		if (bidPrice.getValue() < startPrice.getValue()) {
			throw new BidBelowStartPriceException(bidPrice, startPrice);
		}
	}

	private void validateBidExceedsCurrentHighest(Auction auction, Price bidPrice) {
		Price currentHighestBidPrice = bidReader.findHighestBid(auction.getAuctionId())
			.map(Bid::getBidPrice)
			.orElse(new Price(0L));

		if (bidPrice.getValue() <= currentHighestBidPrice.getValue()) {
			throw new BidNotExceedingCurrentHighestException(bidPrice, currentHighestBidPrice);
		}
	}

	private void validateBidBelowInstantBuyPrice(Auction auction, Price bidPrice) {
		Price instantBuyPrice = auction.getInstantBuyPrice();

		if (instantBuyPrice != null && bidPrice.getValue() >= instantBuyPrice.getValue()) {
			throw new BidExceedsInstantBuyException(bidPrice, instantBuyPrice);
		}
	}

	@Override
	public void instantBuyValidate(Auction auction, Long buyerMemberId) {
		validateSelfBid(auction, buyerMemberId);
	}
}
