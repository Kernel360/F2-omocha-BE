package org.omocha.api.chat;

import java.time.LocalDateTime;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.response.SliceResponseDto;
import org.omocha.domain.chat.ChatInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "채팅방 API(ChatRoomController)", description = "채팅방 생성 및 조회를 위한 API")
public interface ChatRoomApi {

	@Operation(
		summary = "채팅방 생성(Front 테스트용)",
		description = "특정 경매에 대한 새로운 채팅방은 스케줄러로 동작합니다"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "채팅방 생성 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "409", description = "이미 존재하는 채팅방",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류 발생",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		)
	})
	ResponseEntity<ResultDto> chatRoomAdd(
		@Parameter(description = "경매 ID", required = true, example = "12", in = ParameterIn.PATH)
		@PathVariable Long auctionId,

		@Parameter(description = "인증된 사용자 정보", required = true, in = ParameterIn.COOKIE)
		@AuthenticationPrincipal UserPrincipal userPrincipal
	);

	@Operation(
		summary = "사용자의 참여중인 채팅방 목록 조회",
		description = "인증된 사용자가 참여한 모든 채팅방을 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "채팅방 목록 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(
			responseCode = "401", description = "인증되지 않은 사용자",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(
			responseCode = "500", description = "서버 오류 발생",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		)
	})
	ResponseEntity<ResultDto<SliceResponseDto.SliceResponse<ChatInfo.RetrieveMyChatRoom>>> myChatRoomList(
		@Parameter(description = "인증된 사용자 정보", required = true, in = ParameterIn.COOKIE)
		@AuthenticationPrincipal UserPrincipal userPrincipal,

		@Parameter(description = "페이지 번호 (기본값 : 0)", example = "0", required = false)
		@RequestParam(defaultValue = "0") int page,

		@Parameter(description = "페이지 크기 (기본값 : 10)", example = "10", required = false)
		@RequestParam(defaultValue = "10") int size,

		@Parameter(description = "정렬 기준", example = "정렬 기준 1. 새로운 채팅방 2. 최근 메시지", required = false)
		@RequestParam(required = false) String sort
	);

	@Operation(
		summary = "채팅방 메시지 조회",
		description = "특정 채팅방의 모든 메시지를 페이징 처리하여 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메시지 조회 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(
			responseCode = "500", description = "서버 오류 발생",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))
		)
	})
	ResponseEntity<ResultDto<SliceResponseDto.SliceResponse<ChatInfo.RetrieveChatRoomMessage>>> chatMessageList(
		@Parameter(description = "채팅방 ID", required = true, example = "1", in = ParameterIn.PATH)
		@PathVariable Long roomId,

		@Parameter(description = "인증된 사용자 정보", required = true, in = ParameterIn.COOKIE)
		@AuthenticationPrincipal UserPrincipal userPrincipal,

		@Parameter(description = "cursor 기준", example = "2024-10-31T09:15:00", required = false)
		LocalDateTime cursor,

		@Parameter(description = "페이지 Sort", example = "createdAt", required = false)
		String sort,

		@Parameter(description = "페이지 Direction", example = "DESC", required = false)
		String direction,

		@Parameter(description = "페이지 크기", example = "10", required = false)
		int size
	);

}
