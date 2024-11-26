package org.omocha.infra.auction;

import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionStore;
import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageProvider;
import org.omocha.infra.auction.repository.AuctionRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionStoreImpl implements AuctionStore {

	private final AuctionRepository auctionRepository;
	private final ImageProvider imageProvider;

	@Override
	public Auction store(Auction auction) {
		return auctionRepository.save(auction);
	}

	@Override
	public void removeAuction(Auction auction) {
		auctionRepository.delete(auction);
		imageProvider.deleteFile(auction.getThumbnailPath());
		List<Image> images = auction.getImages();
		for (Image image : images) {
			imageProvider.deleteFile(image.getImagePath());
		}
	}
}
