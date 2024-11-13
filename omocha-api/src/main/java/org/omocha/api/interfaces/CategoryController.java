package org.omocha.api.interfaces;

import java.util.List;

import org.omocha.api.application.CategoryFacade;
import org.omocha.api.interfaces.dto.CategoryDto;
import org.omocha.api.interfaces.mapper.CategoryDtoMapper;
import org.omocha.domain.auction.CategoryCommand;
import org.omocha.domain.auction.CategoryInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/categories")
public class CategoryController {

	private final CategoryFacade categoryFacade;
	private final CategoryDtoMapper categoryMapper;

	@PostMapping
	public ResponseEntity<CategoryDto.CategoryAddResponse> categoryAdd(
		@RequestBody CategoryDto.CategoryAddRequest request
	) {
		CategoryCommand.AddCategory addCommand = categoryMapper.toCommand(request);

		Long categoryId = categoryFacade.addCategory(addCommand);

		CategoryDto.CategoryAddResponse response = categoryMapper.toResponse(categoryId);

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<CategoryInfo.CategoryResponse>> categoryDetails() {
		List<CategoryInfo.CategoryResponse> response = categoryFacade.retrieveCategories();

		return ResponseEntity.ok(response);
	}

}
