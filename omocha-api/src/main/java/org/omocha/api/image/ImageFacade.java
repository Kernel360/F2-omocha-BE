package org.omocha.api.image;

import org.omocha.domain.image.ImageCommand;
import org.omocha.domain.image.ImageInfo;
import org.omocha.domain.image.ImageService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageFacade {

	private final ImageService imageService;

	public ImageInfo.AddImage addImage(ImageCommand.AddImage addCommand) {
		return imageService.addImage(addCommand);
	}

	public void deleteImage(ImageCommand.DeleteImage deleteCommand) {
		imageService.deleteImage(deleteCommand);
	}
}
