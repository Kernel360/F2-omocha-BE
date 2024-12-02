package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ImagePathNotFoundException extends ImageException {
	public ImagePathNotFoundException(String imagePath) {
		super(
			ErrorCode.IMAGE_PATH_NOT_FOUND,
			"S3 이미지 경로를 찾지 못했습니다. Image path: {}" + imagePath
		);
	}
}
