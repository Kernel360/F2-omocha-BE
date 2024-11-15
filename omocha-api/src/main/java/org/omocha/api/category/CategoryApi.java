package org.omocha.api.category;

import java.util.List;

import org.omocha.api.category.dto.CategoryDto;
import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.category.CategoryInfo;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "카테고리 API(CategoryController)", description = "카테고리 생성, 전체 조회, 계층 조회 API 입니다.")
public interface CategoryApi {

	@Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 생성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<CategoryDto.CategoryAddResponse>> categoryAdd(
		@Parameter(description = "카테고리 생성 조건", required = true)
		CategoryDto.CategoryAddRequest request
	);

	@Operation(summary = "카테고리 계층 조회", description = "카테고리 계층 목록을 조회합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공적으로 경매 목록을 조회했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<List<CategoryInfo.CategoryResponse>>> categoryHierarchy(
		@Parameter(description = "카테고리 ID", required = true)
		Long categoryId
	);

	@Operation(summary = "카테고리 전체 조회", description = "카테고리 전체 리스트를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매 상세 정보 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	public ResponseEntity<ResultDto<List<CategoryInfo.CategoryResponse>>> categoryDetails();

}
