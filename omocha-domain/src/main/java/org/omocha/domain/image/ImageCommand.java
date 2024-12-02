package org.omocha.domain.image;

import org.springframework.web.multipart.MultipartFile;

public class ImageCommand {

	public record AddAuctionImage(
		String fileName,
		String imagePath
	) {
		public Image toEntity(
			String fileName,
			String imagePath
		) {
			return Image.builder()
				.fileName(fileName)
				.imagePath(imagePath)
				.build();
		}
	}

	public record AddImage(
		MultipartFile image
	) {
		public Image toEntity(
			String imagePath,
			String fileName
		) {
			return Image.builder()
				.imagePath(imagePath)
				.fileName(fileName)
				.build();
		}
	}

	public record DeleteImage(
		String imagePath
	) {
	}
}
