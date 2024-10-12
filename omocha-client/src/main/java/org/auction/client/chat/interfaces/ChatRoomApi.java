package org.auction.client.chat.interfaces;

import org.auction.client.chat.interfaces.response.ChatRoomListResponse;
import org.auction.client.chat.interfaces.response.CreateChatRoomResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
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
		@ApiResponse(responseCode = "201", description = "채팅방이 성공적으로 생성되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "409", description = "이미 존재하는 채팅방입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<CreateChatRoomResponse>> chatRoomSave(
		@Parameter(
			description = "경매 ID",
			required = true,
			example = "12",
			in = ParameterIn.PATH
		)
		@PathVariable Long auctionId,

		@Parameter(
			description = "인증된 사용자 정보",
			required = true,
			in = ParameterIn.COOKIE
		)
		@AuthenticationPrincipal UserPrincipal userPrincipal
	);

	@Operation(
		summary = "채팅방 목록 조회",
		description = "인증된 사용자가 참여한 모든 채팅방 목록을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "채팅방 목록 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<ChatRoomListResponse>> findChatRooms(
		@Parameter(
			description = "인증된 사용자 정보",
			required = true,
			in = ParameterIn.HEADER
		)
		@AuthenticationPrincipal UserPrincipal userPrincipal
	);
}