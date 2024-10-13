package org.auction.client.chat.interfaces;

import org.auction.client.chat.interfaces.response.ChatRoomDetailsResponse;
import org.auction.client.chat.interfaces.response.ChatRoomResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.common.dto.SliceResponse;
import org.auction.client.jwt.UserPrincipal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ChatRoomApi {

	@Operation(
		summary = "채팅방 생성", description = "특정 경매에 대한 채팅방을 생성합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201", description = "채팅방 생성 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		),
		@ApiResponse(
			responseCode = "400", description = "잘못된 요청",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		),
		@ApiResponse(
			responseCode = "401", description = "인증되지 않은 사용자",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		),
		@ApiResponse(
			responseCode = "409", description = "이미 존재하는 채팅방",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		),
		@ApiResponse(
			responseCode = "500", description = "서버 오류 발생",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		)
	})
	ResponseEntity<ResultDto<ChatRoomResponse>> chatRoomSave(
		@Parameter(description = "경매 ID", required = true, example = "12", in = ParameterIn.PATH)
		@PathVariable Long auctionId,

		@Parameter(description = "인증된 사용자 정보", required = true, in = ParameterIn.COOKIE)
		@AuthenticationPrincipal UserPrincipal userPrincipal
	);

	@Operation(
		summary = "채팅방 메시지 조회",
		description = "특정 채팅방의 모든 메시지를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "채팅방 메시지 조회 성공",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResultDto.class)
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증되지 않은 사용자",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResultDto.class)
			)
		),
		@ApiResponse(
			responseCode = "403",
			description = "채팅방 접근 권한이 없는 사용자",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResultDto.class)
			)
		),
		@ApiResponse(
			responseCode = "404",
			description = "채팅방을 찾을 수 없음",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResultDto.class)
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류 발생",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResultDto.class)
			)
		)
	})
	ResponseEntity<ResultDto<ChatRoomDetailsResponse>> chatRoomMessageLists(
		@Parameter(
			description = "채팅방 ID",
			required = true,
			example = "1",
			in = ParameterIn.PATH
		)
		@PathVariable Long roomId,

		@Parameter(
			description = "인증된 사용자 정보",
			required = true,
			in = ParameterIn.HEADER
		)
		@AuthenticationPrincipal UserPrincipal userPrincipal,

		@Parameter(
			description = "페이징 정보",
			required = false,
			in = ParameterIn.QUERY
		)
		Pageable pageable
	);

	@Operation(
		summary = "채팅방 목록 조회",
		description = "인증된 사용자가 참여한 모든 채팅방을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200", description = "채팅방 목록 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		),
		@ApiResponse(
			responseCode = "401", description = "인증되지 않은 사용자",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		),
		@ApiResponse(
			responseCode = "500", description = "서버 오류 발생",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		)
	})
	ResponseEntity<ResultDto<SliceResponse>> chatRoomsLists(
		@Parameter(description = "인증된 사용자 정보", required = true, in = ParameterIn.COOKIE)
		@AuthenticationPrincipal UserPrincipal userPrincipal,

		@Parameter(description = "페이지 정보", example = "0", in = ParameterIn.QUERY)
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	);
}