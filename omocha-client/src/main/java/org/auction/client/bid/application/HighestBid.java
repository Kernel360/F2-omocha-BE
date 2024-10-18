package org.auction.client.bid.application;

import java.util.HashMap;
import java.util.Map;

import org.auction.domain.bid.entity.BidEntity;

public class HighestBid {

	// TODO : 인메모리 DB 쓸 경우 수정 필요
	protected static Map<Long, BidEntity> highestBidMap = new HashMap<>();

	public static void setHighestBid(
		Long auctionId,
		BidEntity highestBid
	) {
		highestBidMap.put(auctionId, highestBid);
	}

	public static BidEntity getHighestBid(
		Long auctionId
	) {
		return highestBidMap.get(auctionId);
	}
	
	public static boolean hasHighestBid(
		Long auctionId
	) {
		return highestBidMap.containsKey(auctionId);
	}

	// TODO : 일정 시간이 지난 이후 삭제처리 해야함
	public static void removeHighestBid(
		Long auctionId
	) {
		highestBidMap.remove(auctionId);
	}

}
