package org.omocha.domain.auction;

import java.util.List;

public interface CategoryService {
	Long addCategory(CategoryCommand.AddCategory addCommand);

	List<CategoryInfo.CategoryResponse> retrieveCategories();

	// Category updateCategory(CategoryDto.CategoryUpdateRequest request);
	
}
