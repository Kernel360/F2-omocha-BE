package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageDeleteFailException extends ImageException {
	public ImageDeleteFailException(String imagePath) {
		super(
			ErrorCode.IMAGE_DELETE_FAIL,
			"S3 이미지 삭제 실패했습니다. Image path: {}" + imagePath
		);
	}
}
