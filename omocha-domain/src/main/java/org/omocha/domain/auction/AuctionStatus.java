package org.omocha.domain.auction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuctionStatus {
	PREBID("PREBID"), BIDDING("BIDDING"), NO_BIDS("NO_BIDS"), CONCLUDED("CONCLUDED"), COMPLETED("COMPLETED");

	private final String description;
}
