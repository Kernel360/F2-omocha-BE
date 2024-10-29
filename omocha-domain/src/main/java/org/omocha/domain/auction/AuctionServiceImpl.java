package org.omocha.domain.auction;

import java.util.List;
import java.util.stream.Collectors;

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
	private final AuctionInfoMapper auctionInfoMapper;

	@Override
	@Transactional
	public Long registerAuction(AuctionCommand.RegisterAuction requestAuction) {
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
		Page<Auction> auctions = auctionReader.searchAuctionList(searchAuction, pageable);

		Page<AuctionInfo.AuctionListResponse> auctionInfoPage = auctions.map(auction -> {
			List<String> imagePaths = auction.getImages().stream()
				.map(Image::getImagePath)
				.collect(Collectors.toList());

			return new AuctionInfo.AuctionListResponse(
				auction.getAuctionId(),
				auction.getTitle(),
				auction.getContent(),
				auction.getAuctionStatus(),
				auction.getStartPrice(),
				auction.getBidUnit(),
				auction.getStartDate(),
				auction.getEndDate(),
				auction.getCreatedAt().toLocalDateTime(),
				imagePaths
			);
		});
		return auctionInfoPage;
	}

	@Override
	@Transactional(readOnly = true)
	public AuctionInfo.AuctionDetailResponse retrieveAuctionDetail(AuctionCommand.RetrieveAuction retrieveAuction) {
		Auction auction = auctionReader.findByAuctionId(retrieveAuction.auctionId());

		List<String> imagePaths = auction.getImages().stream()
			.map(Image::getImagePath)
			.collect(Collectors.toList());

		// TODO : nowPrice, concludePrice, bidCount 추가해야함
		return auctionInfoMapper.toResponse(auction, imagePaths);
	}
}
