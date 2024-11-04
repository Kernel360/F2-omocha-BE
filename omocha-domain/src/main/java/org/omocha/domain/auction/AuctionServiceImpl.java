package org.omocha.domain.auction;

import java.util.List;
import java.util.stream.Collectors;

import org.omocha.domain.exception.AuctionHasBidException;
import org.omocha.domain.exception.AuctionImageNotFoundException;
import org.omocha.domain.exception.MemberInvalidException;
import org.omocha.domain.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

	private final AuctionStore auctionStore;
	private final AuctionImagesFactory auctionImagesFactory;
	private final AuctionReader auctionReader;

	@Override
	@Transactional
	public Long addAuction(AuctionCommand.AddAuction addCommand) {
		if (addCommand.images() == null || addCommand.images().isEmpty()) {
			throw new AuctionImageNotFoundException(addCommand.memberId());
		}

		Auction auction = auctionStore.store(addCommand.toEntity());
		auctionImagesFactory.store(auction, addCommand);
		return auction.getAuctionId();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuctionInfo.SearchAuction> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	) {

		// TODO : nowPrice, concludePrice, bidCount 추가해야함
		return auctionReader.getAuctionList(searchAuction, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public AuctionInfo.RetrieveAuction retrieveAuction(AuctionCommand.RetrieveAuction retrieveCommand) {
		Auction auction = auctionReader.getAuction(retrieveCommand.auctionId());

		List<String> imagePaths = auction.getImages().stream()
			.map(Image::getImagePath)
			.collect(Collectors.toList());

		return new AuctionInfo.RetrieveAuction(auction, imagePaths);
	}

	@Override
	@Transactional
	public void removeAuction(AuctionCommand.RemoveAuction removeCommand) {
		Auction auction = auctionReader.getAuction(removeCommand.auctionId());

		Long memberId = removeCommand.memberId();

		if (!auction.getMemberId().equals(memberId)) {
			throw new MemberInvalidException(memberId);
		}

		if (auction.getBidCount() != null && auction.getBidCount() != 0) {
			throw new AuctionHasBidException(auction.getAuctionId());
		}

		auctionReader.removeAuction(auction);
	}

}
