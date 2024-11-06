package org.omocha.domain.auction.bid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HighestBidManager {

	// TODO : 인메모리 DB 쓸 경우 수정 필요
	protected static Map<Long, Bid> highestBidMap = new HashMap<>();

	public static void setHighestBid(Long auctionId, Bid highestBid) {
		highestBidMap.put(auctionId, highestBid);
	}

	public static Bid getHighestBid(Long auctionId) {
		return highestBidMap.get(auctionId);
	}

	public static boolean hasHighestBid(Long auctionId) {
		return highestBidMap.containsKey(auctionId);
	}

	// TODO : 일정 시간이 지난 이후 삭제처리 해야함
	public static void removeHighestBid(Long auctionId) {
		highestBidMap.remove(auctionId);
	}

	// TODO: 추후 getCurrentHighestBidPrice와 함께 사용되는 곳을 확인 후 리팩토링 고려
	// static 메서드이기 때문에 BidReader를 매개변수로 주입함
	public static Optional<Bid> getCurrentHighestBid(Long auctionId, BidReader bidReader) {
		return Optional.ofNullable(getHighestBid(auctionId))
			.or(() -> bidReader.findHighestBid(auctionId));
	}
}