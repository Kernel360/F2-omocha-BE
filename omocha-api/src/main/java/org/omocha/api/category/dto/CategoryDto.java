package org.omocha.api.category.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryDto {

	public record CategoryAddRequest(
		@NotBlank String name,
		Long parentId
	) {
	}

	public record CategoryAddResponse(
		Long categoryId
	) {
	}

	public record CategoryUpdateRequest(
		@NotNull Long categoryId,
		@NotBlank String name,
		Long parentId
	) {
	}

	public record CategoryResponse(
		Long categoryId,
		String name,
		Long parentId,
		List<CategoryResponse> subCategories
	) {
	}

}
