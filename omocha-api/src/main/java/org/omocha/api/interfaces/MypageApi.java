package org.omocha.api.interfaces;

import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.MypageDto;
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

@Tag(name = "마이페이지 API(MyPageController)", description = "사용자 정보, 수정, 경매 물품 내역.")
public interface MypageApi {
	@Operation(summary = "사용자 정보 가져오기", description = "사용자 정보를 가져옵니다.")
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
	ResponseEntity<ResultDto<MypageDto.CurrentMemberInfoResponse>> currentMemberInfo(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal
	);

	@Operation(summary = "사용자 프로필 이미지 변경", description = "사용자의 프로필 이미지를 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "프로필 이미지 수정이 완료되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<MypageDto.ProfileImageModifyResponse>> profileImageModify(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "프로필 이미지", required = true)
		MultipartFile profileImage
	);

	@Operation(summary = "사용자 비밀번호 변경", description = "사용자의 비밀번호를 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "패스워드 수정이 완료되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> passwordModify(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "비밀 번호 변경용 객체")
		MypageDto.PasswordModifyRequest passwordModifyRequest
	);

	@Operation(summary = "사용자 정보 수정하기", description = "사용자의 일반 정보를 수정합니다.", deprecated = true)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "멤버 정보 수정이 완료되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<MypageDto.MemberModifyResponse>> memberInfoModify(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "사용자 일반 정보 변경용 객체", schema = @Schema(implementation = MypageDto.MemberModifyRequest.class))
		MypageDto.MemberModifyRequest memberModifyRequest
	);

	@Operation(summary = "사용자의 경매 물품 내역 조회", description = "사용자의 경매 물품 내역을 조회합니다.")
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
	ResponseEntity<ResultDto<Page<MypageDto.MyAuctionListResponse>>> myAuctionList(
		@Parameter(description = "사용자 객체 정보", required = true)
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
	ResponseEntity<ResultDto<Page<MypageDto.MyBidAuctionResponse>>> myBidAuctionList(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향", example = "DESC")
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
	ResponseEntity<ResultDto<Page<MypageDto.MyBidListResponse>>> myBidList(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "입찰 내역을 조회 할 경매 ID")
		Long auctionId,
		@Parameter(description = "정렬 기준 필드 (예: createdAt, startPrice 등)", example = "createdAt")
		String sort,
		@Parameter(description = "정렬 방향", example = "DESC")
		String direction,
		@ParameterObject
		Pageable pageable
	);
}
