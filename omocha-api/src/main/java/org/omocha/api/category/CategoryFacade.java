package org.omocha.api.category;

import java.util.List;

import org.omocha.domain.category.CategoryCommand;
import org.omocha.domain.category.CategoryInfo;
import org.omocha.domain.category.CategoryService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryFacade {

	private final CategoryService categoryService;

	public Long addCategory(CategoryCommand.AddCategory addCommand) {
		return categoryService.addCategory(addCommand);
	}

	public List<CategoryInfo.CategoryResponse> retrieveCategories() {
		return categoryService.retrieveCategories();
	}

	public List<CategoryInfo.CategoryResponse> retrieveCategoryHierarchy(Long categoryId) {
		return categoryService.retrieveCategoryHierarchy(categoryId);
	}

}
