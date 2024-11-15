package org.omocha.domain.category;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryStore categoryStore;
	private final CategoryReader categoryReader;

	@Override
	@Transactional
	public Long addCategory(CategoryCommand.AddCategory addCommand) {
		Category parentCategory = null;
		if (addCommand.parentId() != null) {
			parentCategory = categoryReader.getCategory(addCommand.parentId());
		}
		Category store = categoryStore.categoryStore(addCommand.toEntity(parentCategory));
		return store.getCategoryId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryInfo.CategoryResponse> retrieveCategories() {

		return categoryReader.getAllCategories();
	}

	@Override
	public List<CategoryInfo.CategoryResponse> retrieveCategoryHierarchy(Long categoryId) {
		return categoryReader.getCategoryHierarchyUpwards(categoryId);
	}

}
