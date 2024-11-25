package org.omocha.api.qna;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.qna.dto.QuestionDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "질문 API(QuestionController)", description = "질문 조회, 생성, 수정하는 API 입니다.")
public interface QuestionApi {

	@Operation(summary = "질문 답변 통합 조회", description = "질문과 답변을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "답변 조회에 성공하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<QuestionDto.QnaListResponse>>> qnaList(
		@Parameter(description = "통합 조회 할 경매 ID")
		Long auctionId,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

	@Operation(summary = "질문 생성", description = "질문을 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "질문이 성공적으로 생성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "질문을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<QuestionDto.QuestionAddResponse>> questionAdd(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		QuestionDto.QuestionAddRequest questionAddRequest
	);

	@Operation(summary = "질문 수정", description = "질문을 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "질문이 성공적으로 수정되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "회원이 일치하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "403", description = "수정, 삭제가 거부되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "질문를 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<QuestionDto.QuestionModifyResponse>> questionModify(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "수정 할 질문 ID")
		Long questionId,
		QuestionDto.QuestionModifyRequest questionModifyRequest
	);

	@Operation(summary = "질문 삭제", description = "질문를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "질문이 성공적으로 삭제되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "회원이 일치하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "403", description = "수정, 삭제가 거부되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "질문을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> questionRemove(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "삭제 할 질문 ID")
		Long questionId
	);
}
