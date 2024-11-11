package org.omocha.domain.auction;

import java.util.ArrayList;
import java.util.List;

public class CategoryInfo {

	public record AddCategory(
		Long categoryId
	) {
	}

	public record CategoryResponse(
		Long categoryId,
		String name,
		Long parentId,
		List<CategoryResponse> subCategories

	) {
		public static CategoryResponse toResponse(Category category) {
			return new CategoryResponse(
				category.getCategoryId(),
				category.getName(),
				category.getParent() != null ? category.getParent().getCategoryId() : null,
				new ArrayList<>()
			);
		}

		/*public static CategoryResponse toResponse(Long categoryId, String name, Long parentId,
			List<CategoryResponse> subCategories) {
			return new CategoryResponse(
				categoryId,
				name,
				parentId,
				new ArrayList<>()
			);
		}*/

	}

	public record CategoryDetail(
		Long categoryId,
		String name
	) {

	}

}
