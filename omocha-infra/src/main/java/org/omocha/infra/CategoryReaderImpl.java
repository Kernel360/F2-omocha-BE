package org.omocha.infra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omocha.domain.auction.Category;
import org.omocha.domain.auction.CategoryInfo;
import org.omocha.domain.auction.CategoryReader;
import org.omocha.domain.exception.CategoryNotFoundException;
import org.omocha.infra.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryReaderImpl implements CategoryReader {

	private final CategoryRepository categoryRepository;

	@Override
	public Category getCategory(Long categoryId) {
		return categoryRepository.findById(categoryId)
			.orElseThrow(() -> new CategoryNotFoundException(categoryId));
	}

	@Override
	public List<CategoryInfo.CategoryResponse> getAllCategories() {
		List<Category> allCategories = categoryRepository.findAllWithSubCategories();

		Map<Long, CategoryInfo.CategoryResponse> categoryDtoMap = new HashMap<>();
		for (Category category : allCategories) {
			CategoryInfo.CategoryResponse categoryResponse = CategoryInfo.CategoryResponse.toResponse(category);
			categoryDtoMap.put(category.getCategoryId(), categoryResponse);
		}

		List<CategoryInfo.CategoryResponse> rootCategories = new ArrayList<>();
		for (Category category : allCategories) {
			CategoryInfo.CategoryResponse categoryResponse = categoryDtoMap.get(category.getCategoryId());
			if (category.getParent() == null) {
				rootCategories.add(categoryResponse);
			} else {
				CategoryInfo.CategoryResponse parentDto = categoryDtoMap.get(category.getParent().getCategoryId());
				parentDto.subCategories().add(categoryResponse);
			}
		}

		return rootCategories;
	}

	@Override
	public List<CategoryInfo.CategoryResponse> getCategoryHierarchyUpwards(Long categoryId) {

		if (categoryId == null) {
			return Collections.emptyList();
		}

		Category category = getCategory(categoryId);
		return categoryHierarchyUpwards(category);
	}

	@Override
	public List<Long> getSubCategoryIds(Long categoryId) {
		return categoryRepository.getSubCategoryIds(categoryId);
	}

	private List<CategoryInfo.CategoryResponse> categoryHierarchyUpwards(Category category) {
		// rootCategory: 최종적으로 반환하는 최상위 ROOT
		// nestedCategory: 현재까지 subcategory와 중첩된 카테고리
		// currentCategory: 현재 처리중인 카테고리
		CategoryInfo.CategoryResponse rootCategory = null;
		CategoryInfo.CategoryResponse nestedCategory = null;
		Category currentCategory = category;

		while (currentCategory != null) {
			CategoryInfo.CategoryResponse categoryResponse = CategoryInfo.CategoryResponse.toResponse(
				currentCategory);

			if (nestedCategory == null) {
				nestedCategory = categoryResponse;
			} else {
				categoryResponse.subCategories().add(nestedCategory);
				nestedCategory = categoryResponse;
			}

			currentCategory = currentCategory.getParent();
		}

		rootCategory = nestedCategory;
		return List.of(rootCategory);
	}

}
