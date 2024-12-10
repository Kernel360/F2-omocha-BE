package org.omocha.api.bid;

import java.util.List;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.bid.dto.BidDto;
import org.omocha.api.common.response.ResultDto;
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

@Tag(name = "입찰 API(BidController)", description = "입찰 관련 API 입니다.")
public interface BidApi {

	@Operation(summary = "입찰 목록 가져오기", description = "경매의 입찰 목록을 가져옵니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "입찰 목록을 성공적으로 가져왔습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<List<BidDto.BidListResponse>>> bidList(
		@Parameter(description = "입찰 목록을 확인 할 경매 id값", required = true)
		Long auctionId
	);

	@Operation(summary = "입찰하기", description = "해당 경매에 입찰합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "입찰에 성공하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "409", description = "입찰에 실패하였습니다. 선입찰자가 존재합니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<BidDto.BidAddResponse>> bidAdd(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "입찰 할 경매 id값", required = true)
		Long auctionId,
		@RequestBody(description = "입찰 금액", required = true)
		BidDto.BidAddRequest addRequest
	);

	@Operation(summary = "현재가 조회", description = "해당 경매의 현재가를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "현재가 조회에 성공하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<BidDto.NowPriceResponse>> nowPrice(
		@Parameter(description = "현재가를 조회할 경매 id값", required = true)
		Long auctionId
	);

	@Operation(summary = "즉시 구매", description = "경매 품목을 즉시 구매합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "즉시 구매를 성공적으로 완료하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> instantBuy(
		@Parameter(description = "사용자 객체 정보", required = true) UserPrincipal userPrincipal,
		@Parameter(description = "경매 ID") Long auctionId
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
	ResponseEntity<ResultDto<Page<BidDto.MyBidListResponse>>> myBidList(
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
