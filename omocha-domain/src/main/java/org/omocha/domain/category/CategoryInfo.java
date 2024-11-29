package org.omocha.domain.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryInfo {

	public record CategoryResponse(
		Long categoryId,
		String name,
		Long parentId,
		int orderIndex,
		List<CategoryResponse> subCategories

	) {
		public static CategoryResponse toResponse(Category category) {
			return new CategoryResponse(
				category.getCategoryId(),
				category.getName(),
				category.getParent() != null ? category.getParent().getCategoryId() : 0,
				category.getOrderIndex(),
				new ArrayList<>()
			);
		}

	}

}
