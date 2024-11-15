package org.omocha.infra.likes;

import java.time.LocalDateTime;

import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.likes.LikeStore;
import org.omocha.infra.likes.repository.LikeRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LikeStoreImpl implements LikeStore {

	private final LikeRepository likeRepository;

	@Override
	public void clickLike(AuctionCommand.LikeAuction likeCommand, LocalDateTime createdAt) {
		likeRepository.clickLike(likeCommand.auctionId(), likeCommand.memberId(), createdAt);
	}

	@Override
	public void unClickLike(AuctionCommand.LikeAuction likeCommand) {
		likeRepository.unClickLike(likeCommand.auctionId(), likeCommand.memberId());
	}
}
