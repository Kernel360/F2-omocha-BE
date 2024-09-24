package org.auction.client.auction.interfaces;

import java.util.List;

import org.auction.client.auction.interfaces.request.CreateAuctionRequest;
import org.auction.client.auction.interfaces.response.AuctionDetailResponse;
import org.auction.client.auction.interfaces.response.CreateAuctionResponse;
import org.auction.client.common.dto.ResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AuctionApi {

	// REFACTOR : exception 처리를 제대로 공부한 후 refactoring 해야함
	@Operation(summary = "경매 생성", description = "새로운 경매를 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 생성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<CreateAuctionResponse>> auctionSave(
		@Parameter
		Long userId,
		@Parameter(description = "경매 요청 데이터", required = true)
		CreateAuctionRequest auctionRequest,
		@Parameter(description = "이미지 파일 리스트", required = false, schema = @Schema(type = "array", format = "binary"))
		List<MultipartFile> images
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
	ResponseEntity<ResultDto<AuctionDetailResponse>> auctionDetails(
		@Parameter(description = "경매 ID", required = true) Long id);

	@Operation(summary = "경매 삭제", description = "경매 ID를 사용하여 경매를 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "경매가 성공적으로 삭제되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "403", description = "권한이 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "해당 ID의 경매가 존재하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> auctionRemove(
		@Parameter(description = "경매 ID", required = true) Long id);
}