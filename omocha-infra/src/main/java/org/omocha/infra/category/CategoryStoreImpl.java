package org.omocha.infra.category;

import org.omocha.domain.category.Category;
import org.omocha.domain.category.CategoryReader;
import org.omocha.domain.category.CategoryStore;
import org.omocha.infra.category.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryStoreImpl implements CategoryStore {

	private final CategoryRepository categoryRepository;
	private final CategoryReader categoryReader;

	@Override
	public Category categoryStore(Category category) {
		return categoryRepository.save(category);
	}

}
