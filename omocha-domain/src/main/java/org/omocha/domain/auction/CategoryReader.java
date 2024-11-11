package org.omocha.domain.auction;

import java.util.List;

public interface CategoryReader {
	Category getCategory(Long categoryId);

	List<CategoryInfo.CategoryResponse> getAllCategories();

	List<CategoryInfo.CategoryResponse> getCategoryHierarchyUpwards(Long categoryId);

	List<Long> getSubCategoryIds(Long categoryId);
}
