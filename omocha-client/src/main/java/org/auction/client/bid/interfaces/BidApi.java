package org.auction.client.bid.interfaces;

import java.security.Principal;
import java.util.List;

import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.client.common.dto.ResultDto;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface BidApi {

	@Operation(summary = "입찰 목록 가져오기", description = "경매의 입찰 목록을 가져옵니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "입찰 목록을 성공적으로 가져왔습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<List<BidResponse>>> bidList(
		@Parameter(description = "입찰 목록을 확인 할 경매", required = true)
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
	ResponseEntity<ResultDto<CreateBidResponse>> bidAdd(
		@Parameter(description = "사용자 객체 정보", required = true)
		Principal principal,
		@Parameter(description = "입찰 할 경매", required = true)
		Long auctionId,
		@Parameter(description = "입찰 금액", required = true)
		CreateBidRequest createBidRequest
	);

}
