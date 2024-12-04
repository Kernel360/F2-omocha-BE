package org.omocha.api.image;

import org.omocha.api.common.response.ResultDto;
import org.omocha.api.image.dto.ImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "S3 Image API(ImageController)", description = "사용자가 S3에 이미지를 업로드/삭제하는 API 입니다")
public interface ImageApi {

	@Operation(summary = "S3에 이미지 업로드", description = "사용자가 S3에 이미지를 업로드 합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "유저 정보를 성공적으로 반환하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<ImageDto.ImageAddResponse>> imageAdd(
		@Parameter(description = "이미지 파일 리스트", required = true)
		MultipartFile image
	);

	@Operation(summary = "S3에 이미지 삭제", description = "사용자가 S3에 이미지를 삭제 합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "유저 정보를 성공적으로 반환하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> imageDelete(
		@Parameter(description = "이미지 Path 데이터", required = true)
		ImageDto.ImageDeleteRequest request
	);

}
