package org.omocha.api.application;

import java.util.List;

import org.omocha.domain.auction.CategoryCommand;
import org.omocha.domain.auction.CategoryInfo;
import org.omocha.domain.auction.CategoryService;
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

}
