package org.auction.client.bid.application;

import java.util.HashMap;
import java.util.Map;

public class HighestBid {

	// TODO : 인메모리 DB 쓸 경우 수정 필요
	protected static Map<Long, Long> highestBidMap = new HashMap<>();

	public static void setHighestBid(Long auctionId, Long bidPrice) {
		highestBidMap.put(auctionId, bidPrice);
	}

	public static Long getHighestBid(Long auctionId) {
		return highestBidMap.get(auctionId);
	}

	public static boolean hasHighestBid(Long auctionId) {
		return highestBidMap.containsKey(auctionId);
	}

	public static void removeHighestBid(Long auctionId) {
		highestBidMap.remove(auctionId);
	}

}
