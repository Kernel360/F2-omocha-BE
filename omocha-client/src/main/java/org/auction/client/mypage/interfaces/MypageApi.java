package org.auction.client.mypage.interfaces;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
import org.auction.client.mypage.interfaces.response.MemberInfoResponse;
import org.auction.client.mypage.interfaces.response.MypageAuctionListResponse;
import org.auction.client.mypage.interfaces.response.MypageBidListResponse;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface MypageApi {
	@Operation(summary = "사용자 정보 가져오기", description = "사용자 정보를 가져옵니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 정보를 가져왔습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<MemberInfoResponse>> getMe(
		@Parameter(description = "사용자 객체 정보", required = true)
		@AuthenticationPrincipal UserPrincipal user
	);

	@Operation(summary = "사용자 정보 수정하기", description = "사용자 정보를 수정합니다.", deprecated = true)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "사용자 정보를 성공적으로 수정했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	void UserInfoModify(
		@Parameter(description = "사용자 객체 정보", required = true)
		@AuthenticationPrincipal UserPrincipal userPrincipal
	);

	@Operation(summary = "사용자의 경매 물품 내역 조회", description = "사용자의 경매 물품 내역을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매 물품 내역 조회에 성공하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<MypageAuctionListResponse>>> TransactionAuctionList(
		@Parameter(description = "사용자 객체 정보", required = true)
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@Parameter(description = "경매 상태 필터", schema = @Schema(implementation = AuctionStatus.class))
		AuctionStatus auctionStatus,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

	@Operation(summary = "사용자의 입찰 내역 조회", description = "사용자의 입찰 내역을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "입찰 내역 조회에 성공하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Page<MypageBidListResponse>>> TransactionBidList(
		@Parameter(description = "사용자 객체 정보", required = true)
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향 (ASC 또는 DESC)", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);

}
