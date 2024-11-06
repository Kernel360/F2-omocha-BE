package org.omocha.api.interfaces;

import java.util.List;

import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.BidDto;
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
}
