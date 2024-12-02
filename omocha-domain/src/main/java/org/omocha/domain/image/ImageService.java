package org.omocha.domain.image;

public interface ImageService {
	ImageInfo.AddImage addImage(ImageCommand.AddImage addCommand);

	void deleteImage(ImageCommand.DeleteImage deleteCommand);
}
