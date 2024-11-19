package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageDeleteFailException extends ImageException {
	public ImageDeleteFailException(String imagePath) {
		super(
			ErrorCode.IMAGE_DELETE_FAIL,
			"Failed to delete file from S3: {}" + imagePath
		);
	}
}
