package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ImageNotFoundException extends ImageException {
	public ImageNotFoundException(String imagePath) {
		super(
			ErrorCode.IMAGE_NOT_FOUND,
			"S3 이미지를 찾지 못했습니다. Image path: {}" + imagePath
		);
	}
}
