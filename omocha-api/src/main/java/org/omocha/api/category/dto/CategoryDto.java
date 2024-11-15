package org.omocha.api.category.dto;

import java.util.List;

public class CategoryDto {

	public record CategoryAddRequest(
		String name,
		Long parentId
	) {
	}

	public record CategoryAddResponse(
		Long categoryId
	) {
	}

	public record CategoryUpdateRequest(
		Long categoryId,
		String name,
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
