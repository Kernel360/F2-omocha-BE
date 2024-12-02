package org.omocha.infra.image;

import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageReader;
import org.omocha.domain.image.exception.ImageNotFoundException;
import org.omocha.infra.image.repository.ImageRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageReaderImpl implements ImageReader {

	private final ImageRepository imageRepository;

	@Override
	public Image getImage(String imagePath) {
		return imageRepository.findImageByImagePath(imagePath)
			.orElseThrow(() -> new ImageNotFoundException(imagePath));
	}
}
