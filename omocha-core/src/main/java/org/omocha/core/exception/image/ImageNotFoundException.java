package org.omocha.core.exception.image;

import org.omocha.client.common.code.ImageCode;

public class ImageNotFoundException extends ImageException {
	public ImageNotFoundException(
		ImageCode imageCode
	) {
		super(imageCode);
	}

	public ImageNotFoundException(
		ImageCode imageCode,
		String detailMessage
	) {
		super(imageCode, detailMessage);
	}
}
