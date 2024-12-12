package org.omocha.infra.image;

import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageCommand;
import org.omocha.domain.image.ImageConverter;
import org.omocha.domain.image.ImageInfo;
import org.omocha.domain.image.ImageProvider;
import org.omocha.domain.image.ImageReader;
import org.omocha.domain.image.ImageService;
import org.omocha.domain.image.ImageStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageProvider imageProvider;
	private final ImageStore imageStore;
	private final ImageReader imageReader;
	private final ImageConverter imageConverter;

	@Override
	@Transactional
	public ImageInfo.AddImage addImage(ImageCommand.AddImage addCommand) {
		String imagePath = imageConverter.convertToWebp(addCommand.image());
		String fileName = addCommand.image().getOriginalFilename();

		Image image = addCommand.toEntity(imagePath, fileName);
		imageStore.store(image);

		return new ImageInfo.AddImage(imagePath);
	}

	@Override
	@Transactional
	public void deleteImage(ImageCommand.DeleteImage deleteCommand) {
		String imagePath = deleteCommand.imagePath();
		imageProvider.deleteFile(imagePath);
		Image image = imageReader.getImage(imagePath);
		imageStore.delete(image);
	}
}
