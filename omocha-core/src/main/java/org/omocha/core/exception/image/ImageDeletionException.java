package org.omocha.core.exception.image;

import org.omocha.client.common.code.ImageCode;

public class ImageDeletionException extends ImageException {
	public ImageDeletionException(
		ImageCode imageCode
	) {
		super(imageCode);
	}

	public ImageDeletionException(
		ImageCode imageCode,
		String detailMessage
	) {
		super(imageCode, detailMessage);
	}
}
