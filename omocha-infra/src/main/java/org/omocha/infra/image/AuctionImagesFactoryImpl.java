package org.omocha.infra.image;

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
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionImagesFactoryImpl implements AuctionImagesFactory {

	private final ImageProvider imageProvider;
	private final ImageStore imageStore;

	@Override
	public List<Image> store(Auction auction, AuctionCommand.AddAuction addCommand) {

		List<MultipartFile> images = addCommand.images();

		MultipartFile multipartFile = images.get(0);

		String thumbnail = imageProvider.uploadFile(multipartFile);
		auction.thumbnailPathUpload(thumbnail);

		return images.stream()
			.skip(1)
			.map(auctionImageRequest -> {
				String imagePath = imageProvider.uploadFile(auctionImageRequest);
				String fileName = auctionImageRequest.getOriginalFilename();

				ImageCommand.AddAuctionImage addImageCommand = new ImageCommand.AddAuctionImage
					(fileName, imagePath);

				Image image = addImageCommand.toEntity(fileName, imagePath);
				imageStore.store(image);

				return image;
			}).collect(Collectors.toList());
	}
}
