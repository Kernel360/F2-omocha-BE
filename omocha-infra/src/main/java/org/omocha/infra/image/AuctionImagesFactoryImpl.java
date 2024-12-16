package org.omocha.infra.image;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionImagesFactory;
import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageConverter;
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
	private final ImageConverter imageConverter;

	@Override
	public List<Image> store(Auction auction, AuctionCommand.AddAuction addCommand) {

		List<MultipartFile> images = addCommand.images();

		MultipartFile firstImage = images.get(0);

		Image thumbnailImage = processThumbnail(auction, firstImage, firstImage.getOriginalFilename());

		List<Image> otherImages = images.stream()
			.skip(1)
			.map(this::processImage)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		auction.getImages().add(thumbnailImage);
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
  
  private Image processThumbnail(Auction auction, MultipartFile file, String originalFileName) {
		String imagePath = imageConverter.convertToWebp(file);

		Image thumbnailImage = buildImage(originalFileName, imagePath);

		imageStore.store(thumbnailImage);

		auction.thumbnailPathUpload(imagePath);

		return thumbnailImage;
	}

	private static Image buildImage(String fileName, String imagePath) {
		Image image = Image.builder()
			.fileName(fileName)
			.imagePath(imagePath)
			.build();
		return image;
	}
}
