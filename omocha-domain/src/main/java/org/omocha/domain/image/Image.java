package org.omocha.domain.image;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Image {

	private Long imageId;

	private String fileName;

	private String imagePath;

	@Builder
	public Image(String fileName, String imagePath) {
		this.fileName = fileName;
		this.imagePath = imagePath;
	}

}
