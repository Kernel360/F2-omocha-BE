package org.omocha.domain.auction;

import java.util.List;
import java.util.stream.Collectors;

import org.omocha.domain.exception.AuctionImageNotFoundException;
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
	public Long registerAuction(AuctionCommand.RegisterAuction requestAuction) {
		if (requestAuction.images() == null || requestAuction.images().isEmpty()) {
			throw new AuctionImageNotFoundException(requestAuction.memberId());
		}

		Auction auction = auctionStore.store(requestAuction.toEntity());
		auctionImagesFactory.store(auction, requestAuction);
		return auction.getAuctionId();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuctionInfo.AuctionListResponse> searchAuction(
		AuctionCommand.SearchAuction searchAuction,
		Pageable pageable
	) {

		// TODO : nowPrice, concludePrice, bidCount 추가해야함
		return auctionReader.searchAuctionList(searchAuction, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public AuctionInfo.AuctionDetailResponse retrieveAuctionDetail(AuctionCommand.RetrieveAuction retrieveAuction) {
		Auction auction = auctionReader.getAuction(retrieveAuction.auctionId());

		List<String> imagePaths = auction.getImages().stream()
			.map(Image::getImagePath)
			.collect(Collectors.toList());

		// TODO : nowPrice, concludePrice, bidCount 추가해야함
		return new AuctionInfo.AuctionDetailResponse(auction, imagePaths);
	}

}
