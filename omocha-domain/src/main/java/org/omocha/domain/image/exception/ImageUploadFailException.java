package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ImageUploadFailException extends ImageException {
	public ImageUploadFailException(String fileName) {
		super(
			ErrorCode.IMAGE_UPLOAD_FAIL,
			"S3 이미지 업로드 실패했습니다. File name: " + fileName
		);
	}
}
