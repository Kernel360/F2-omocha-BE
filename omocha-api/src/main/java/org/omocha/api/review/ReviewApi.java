package org.omocha.api.review;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.review.dto.ReviewDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "리뷰 API(ReviewController)", description = "리뷰 관련 API 입니다.")
public interface ReviewApi {

	@Operation(summary = "리뷰 작성하기", description = "경매의 리뷰를 작성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "리뷰가 성공적으로 생성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<ReviewDto.ReviewAddResponse>> reviewAdd(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "리뷰를 작성할 경매 id값", required = true)
		Long auctionId,
		@RequestBody(description = "리뷰 작성 내용", required = true)
		ReviewDto.ReviewAddRequest request
	);

	@Operation(summary = "멤버가 받은 리뷰 목록 조회", description = "해당 멤버가 받은 리뷰 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "리뷰 리스트를 성공적으로 조회하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<ReviewDto.ReceivedReviewListResponse>>> memberReceivedReviewList(
		@Parameter(description = "조회 멤버 id값", required = true)
		Long memberId,
		@Parameter(description = "정렬 기준 필드 (createdAt)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

	@Operation(summary = "내가 받은 리뷰 목록 조회", description = "내가 받은 리뷰 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "리뷰 리스트를 성공적으로 조회하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<ReviewDto.ReceivedReviewListResponse>>> myReceivedReviewList(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "정렬 기준 필드 (createdAt)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

	@Operation(summary = "내가 작성한 리뷰 목록 조회", description = "내가 작성한 리뷰 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "리뷰 리스트를 성공적으로 조회하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<ReviewDto.GivenReviewListResponse>>> myGivenReviewList(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "정렬 기준 필드 (createdAt)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);
}
