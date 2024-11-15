package org.omocha.infra.category.repository;

import java.util.List;

public interface CategoryRepositoryCustom {
	List<Long> getSubCategoryIds(Long categoryId);
}
