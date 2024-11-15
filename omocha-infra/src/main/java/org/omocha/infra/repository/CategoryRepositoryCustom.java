package org.omocha.infra.repository;

import java.util.List;

public interface CategoryRepositoryCustom {
	List<Long> getSubCategoryIds(Long categoryId);
}
