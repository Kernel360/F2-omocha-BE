package org.omocha.domain.auction;

import java.util.List;

public interface CategoryService {
	Long addCategory(CategoryCommand.AddCategory addCommand);

	List<CategoryInfo.CategoryResponse> retrieveCategories();

	List<CategoryInfo.CategoryResponse> retrieveCategoryHierarchy(Long categoryId);
	
	// Category updateCategory(CategoryDto.CategoryUpdateRequest request);

}
