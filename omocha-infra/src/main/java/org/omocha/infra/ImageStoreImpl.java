package org.omocha.infra;

import org.omocha.domain.image.Image;
import org.omocha.domain.image.ImageStore;
import org.omocha.infra.repository.ImageRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageStoreImpl implements ImageStore {

	private final ImageRepository imageRepository;

	@Override
	public Image store(Image image) {
		return imageRepository.save(image);
	}
}
