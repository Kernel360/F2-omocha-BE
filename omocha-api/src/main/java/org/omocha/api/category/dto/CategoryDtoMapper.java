package org.omocha.api.category.dto;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.category.Category;
import org.omocha.domain.category.CategoryCommand;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CategoryDtoMapper {

	CategoryCommand.AddCategory toCommand(CategoryDto.CategoryAddRequest request);

	CategoryDto.CategoryAddResponse toResponse(Long categoryId);

	List<CategoryDto.CategoryResponse> toResponseList(List<Category> categories);

	@Mapping(target = "categoryId", source = "categoryId")
	@Mapping(target = "name", source = "name")
	@Mapping(target = "parentId", source = "parent.categoryId")
	@Mapping(target = "subCategories", source = "subCategories")
	CategoryDto.CategoryResponse toResponse(Category category);
}
