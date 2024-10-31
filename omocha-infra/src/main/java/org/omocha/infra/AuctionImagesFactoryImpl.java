package org.omocha.infra;

import java.util.List;
import java.util.stream.Collectors;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionImagesFactory;
import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageCommand;
import org.omocha.domain.image.ImageProvider;
import org.omocha.domain.image.ImageStore;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionImagesFactoryImpl implements AuctionImagesFactory {

	private final ImageProvider imageProvider;
	private final ImageStore imageStore;

	@Override
	public List<Image> store(Auction auction, AuctionCommand.RegisterAuction requestAuction) {

		String thumbnailPath = imageProvider.uploadFile(requestAuction.thumbnailPath());
		auction.thumbnailPathUpload(thumbnailPath);

		return requestAuction.images().stream()
			.map(auctionImageRequest -> {
				String imagePath = imageProvider.uploadFile(auctionImageRequest);
				String fileName = auctionImageRequest.getOriginalFilename();

				ImageCommand.RegisterAuctionImage registerAuctionImage = new ImageCommand.RegisterAuctionImage
					(fileName, imagePath, auction);

				Image image = registerAuctionImage.toEntity(fileName, imagePath, auction);
				imageStore.store(image);

				return image;
			}).collect(Collectors.toList());

	}
}
