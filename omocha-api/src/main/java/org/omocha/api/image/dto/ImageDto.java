package org.omocha.api.image.dto;

import jakarta.validation.constraints.NotBlank;

public class ImageDto {

	public record ImageAddResponse(
		String imagePath
	) {
	}

	public record ImageDeleteRequest(
		@NotBlank String imagePath
	) {
	}
}
