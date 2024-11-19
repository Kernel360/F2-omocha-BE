package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ImageUploadFailException extends ImageException {
	public ImageUploadFailException(String fileName) {
		super(
			ErrorCode.IMAGE_UPLOAD_FAIL,
			"Failed to upload image to S3. FileName: " + fileName
		);
	}
}
