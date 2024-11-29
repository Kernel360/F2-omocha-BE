package org.omocha.api.auction;

import java.util.List;

import org.omocha.api.auction.dto.AuctionDto;
import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.auction.Auction;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "경매 API(AuctionController)", description = "경매 생성, 상세조회, 전체 조회 API 입니다.")
public interface AuctionApi {

	@Operation(summary = "경매 생성", description = "새로운 경매를 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 생성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "이미지가 없습니다",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<AuctionDto.AuctionAddResponse>> auctionAdd(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "경매 요청 데이터", required = true)
		AuctionDto.AuctionAddRequest auctionRequest,
		@Parameter(description = "이미지 파일 리스트", required = true)
		List<MultipartFile> images
	);

	@Operation(summary = "경매 목록 조회", description = "경매 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공적으로 경매 목록을 조회했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<AuctionDto.AuctionSearchResponse>>> auctionSearchList(
		@Parameter(description = "사용자 객체 정보", required = true) UserPrincipal userPrincipal,
		@Parameter(description = "검색 조건", required = false)
		AuctionDto.AuctionSearchRequest searchRequest,
		@Parameter(description = "카테고리 ID", required = false)
		Long categoryId,
		@Parameter(description = "경매 상태 필터", schema = @Schema(implementation = Auction.AuctionStatus.class))
		Auction.AuctionStatus auctionStatus,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

	@Operation(summary = "경매 상세 조회", description = "경매 ID를 사용하여 경매의 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매 상세 정보 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "해당 ID의 경매가 존재하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<AuctionDto.AuctionDetailsResponse>> auctionDetails(
		@Parameter(description = "경매 ID", required = true) Long auctionId
	);

	@Operation(summary = "경매 삭제", description = "경매 ID를 사용하여 경매를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 삭제되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "경매를 삭제할 수 없습니다 (이미 입찰이 있을 경우, 경매를 생성한 회원이 아님..",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "해당 ID의 경매가 존재하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> auctionRemove(
		@Parameter(description = "사용자 객체 정보", required = true) UserPrincipal userPrincipal,
		@Parameter(description = "경매 ID", required = true) Long auctionId
	);

	@Operation(summary = "경매 찜/찜 취소", description = "경매 ID를 사용하여 경매를 찜하거나 찜취소를 합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 삭제되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<AuctionDto.AuctionLikeResponse>> auctionLike(
		@Parameter(description = "사용자 객체 정보", required = true) UserPrincipal userPrincipal,
		@Parameter(description = "경매 ID", required = true) Long auctionId
	);

	@Operation(summary = "내가 찜한 경매 게시글 조회", description = "내가 찜한 경매 게시글을 전체 조회합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 삭제되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<AuctionDto.AuctionLikeListResponse>>> myAuctionLikeList(
		@Parameter(description = "사용자 객체 정보", required = true) UserPrincipal userPrincipal,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC", required = false)
		String direction,
		int page,
		@Parameter(description = "페이지 크기 (기본값 : 10)", example = "10", required = false)
		int size
	);

	@Operation(summary = "사용자(나)의 경매 물품 내역 조회", description = "사용자(나)의 경매 물품 내역을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매 내역을 성공적으로 조회하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<AuctionDto.MyAuctionListResponse>>> myAuctionList(
		@Parameter(description = "사용자(나) 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "경매 상태 필터", schema = @Schema(implementation = Auction.AuctionStatus.class))
		Auction.AuctionStatus auctionStatus,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

	@Operation(summary = "사용자(상대방)의 경매 물품 내역 조회", description = "사용자(상대방)의 경매 물품 내역을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매 내역을 성공적으로 조회하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<AuctionDto.MemberAuctionListResponse>>> memberAuctionList(
		@Parameter(description = "사용자(상대방) 객체 정보", required = true)
		Long memberId,
		@Parameter(description = "경매 상태 필터", schema = @Schema(implementation = Auction.AuctionStatus.class))
		Auction.AuctionStatus auctionStatus,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향", example = "DESC")
		String direction,
		@Parameter(description = "현재 페이지 (기본값 : 0)", example = "0", required = false)
		int page,
		@Parameter(description = "페이지 크기 (기본값 : 10)", example = "10", required = false)
		int size
	);

	@Operation(summary = "사용자가 입찰한 경매 조회", description = "사용자가 입찰한 경매 내역을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "입찰한 경매 내역을 성공적으로 조회하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<AuctionDto.MyBidAuctionResponse>>> myBidAuctionList(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

}
