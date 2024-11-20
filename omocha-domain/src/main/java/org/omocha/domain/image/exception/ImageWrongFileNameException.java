package org.omocha.domain.image.exception;

import org.omocha.domain.common.code.ErrorCode;

public class ImageWrongFileNameException extends ImageException {
	public ImageWrongFileNameException(String fileName) {
		super(
			ErrorCode.UNSUPPORTED_MEDIA_TYPE,
			"잘못된 형식의 파일 입니다. File name: " + fileName
		);
	}
}
