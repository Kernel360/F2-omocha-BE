package org.omocha.infra;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionStore;
import org.omocha.infra.repository.AuctionRepository;
import org.omocha.infra.repository.LikeRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionStoreImpl implements AuctionStore {

	private final AuctionRepository auctionRepository;
	private final LikeRepository likeRepository;

	@Override
	public Auction store(Auction auction) {
		return auctionRepository.save(auction);
	}

	@Override
	public void clickLike(AuctionCommand.LikeAuction likeCommand) {
		likeRepository.clickLike(likeCommand.auctionId(), likeCommand.memberId());
	}

	@Override
	public void unClickLike(AuctionCommand.LikeAuction likeCommand) {
		likeRepository.unClickLike(likeCommand.auctionId(), likeCommand.memberId());
	}
}
