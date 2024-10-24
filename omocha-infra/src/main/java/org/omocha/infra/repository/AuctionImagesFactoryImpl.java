package org.omocha.infra.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionImagesFactory;
import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageCommand;
import org.omocha.domain.image.ImageService;
import org.omocha.domain.image.ImageStore;
import org.omocha.infra.entity.AuctionEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionImagesFactoryImpl implements AuctionImagesFactory {

	private final ImageService imageService;
	private final ImageStore imageStore;

	@Override
	public List<Image> store(AuctionEntity auction, AuctionCommand.RegisterAuction requestAuction) {
		return requestAuction.images().stream()
			.map(auctionImageRequest -> {
				var imagePath = imageService.uploadFile(auctionImageRequest);
				var fileName = auctionImageRequest.getOriginalFilename();

				var registerAuctionImage = new ImageCommand.RegisterAuctionImage
					(fileName, imagePath, auction);

				var image = registerAuctionImage.toEntity(fileName, imagePath, auction);
				imageStore.store(image);

				return image;
			}).collect(Collectors.toList());

	}
}
