package org.omocha.infra.repository;

import org.auction.domain.image.Image;
import org.auction.domain.image.ImageStore;
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
