package org.omocha.infra.image;

import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionImagesFactory;
import org.omocha.domain.image.Image;
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

		MultipartFile thumbnailFile = images.get(0);

		processThumbnail(auction, thumbnailFile);

		List<Image> otherImages = images.stream()
			.skip(1)
			.map(this::processImage)
			.filter(image -> image != null)
			.toList();

		auction.getImages().addAll(otherImages);

		return otherImages;
	}

	private Image processImage(MultipartFile imageFile) {
		String fileName = imageFile.getOriginalFilename();
		String imagePath = imageProvider.uploadFile(imageFile);

		Image image = buildImage(fileName, imagePath);

		imageStore.store(image);

		return image;
	}

	private void processThumbnail(Auction auction, MultipartFile thumbnailFile) {
		String fileName = thumbnailFile.getOriginalFilename();
		String imagePath = imageProvider.uploadFile(thumbnailFile);

		auction.thumbnailPathUpload(imagePath);

		Image thumbnailImage = buildImage(fileName, imagePath);

		imageStore.store(thumbnailImage);
	}

	private static Image buildImage(String fileName, String imagePath) {
		Image image = Image.builder()
			.fileName(fileName)
			.imagePath(imagePath)
			.build();
		return image;
	}

}
