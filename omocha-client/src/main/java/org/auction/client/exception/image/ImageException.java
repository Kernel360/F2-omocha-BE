package org.auction.client.exception.image;

import org.auction.client.common.code.ImageCode;

import lombok.Getter;

@Getter
public class ImageException extends RuntimeException {

	private final ImageCode imageCode;
	private final String detailMessage;

	public ImageException(
		ImageCode imageCode
	) {
		super(imageCode.getResultMsg());
		this.imageCode = imageCode;
		this.detailMessage = imageCode.getResultMsg();
	}

	public ImageException(
		ImageCode imageCode,
		String detailMessage
	) {
		super(imageCode.getResultMsg());
		this.imageCode = imageCode;
		this.detailMessage = detailMessage;
	}
}
