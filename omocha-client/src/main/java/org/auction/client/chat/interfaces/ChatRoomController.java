package org.auction.client.chat.interfaces;

import static org.auction.client.common.code.ChatCode.*;

import java.time.LocalDateTime;

import org.auction.client.chat.application.ChatRoomService;
import org.auction.client.chat.application.ChatService;
import org.auction.client.chat.interfaces.response.ChatRoomDetailsResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.common.dto.SliceResponse;
import org.auction.client.jwt.UserPrincipal;
import org.auction.domain.chat.domain.dto.ChatRoomInfoDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatroom")
public class ChatRoomController implements ChatRoomApi {

	private final ChatRoomService chatRoomService;
	private final ChatService chatService;

	// EXPLAIN : 채팅방 생성
	@Override
	@PostMapping("/{auctionId}")
	public ResponseEntity<ResultDto<Void>> chatRoomSave(
		@PathVariable Long auctionId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		chatRoomService.addChatRoom(userPrincipal.getMemberEntity(), auctionId);

		ResultDto<Void> resultDto = ResultDto.res(
			CHATROOM_CREATE_SUCCESS.getStatusCode(),
			CHATROOM_CREATE_SUCCESS.getResultMsg()
		);

		return ResponseEntity
			.status(CHATROOM_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	// EXPLAIN : 채팅방 상세 조회 시 채팅방의 정보와 해당 채팅방 대화 내역을 전부 조회한다 (무한 스크롤로 구현)
	@Override
	@GetMapping("/{roomId}")
	public ResponseEntity<ResultDto<ChatRoomDetailsResponse>> chatRoomMessageLists(
		@PathVariable Long roomId,
		@AuthenticationPrincipal
		UserPrincipal userPrincipal,
		@RequestParam(required = false)
		LocalDateTime cursor,
		@PageableDefault(size = 10)
		Pageable pageable
	) {
		log.info("Fetching chat room messages for room ID: {}", roomId);

		ChatRoomDetailsResponse response = chatService.findChatRoomMessages(
			roomId,
			userPrincipal.getMemberEntity(),
			cursor,
			pageable
		);

		ResultDto<ChatRoomDetailsResponse> resultDto = ResultDto.res(
			CHATROOM_DETAILS_AND_MESSAGES_SUCCESS.getStatusCode(),
			CHATROOM_DETAILS_AND_MESSAGES_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(CHATROOM_DETAILS_AND_MESSAGES_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	// EXPLAIN : 내가 참여하고 있는 채팅방 전체 조회 (무한 스크롤로 구현)
	@Override
	@GetMapping
	public ResponseEntity<ResultDto<SliceResponse<ChatRoomInfoDto>>> chatRoomsLists(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {
		log.info("Fetching chat rooms for user ID: {}", userPrincipal.getMemberEntity().getMemberId());

		Slice<ChatRoomInfoDto> chatRooms = chatRoomService.findMyChatRooms(userPrincipal.getMemberEntity(),
			pageable);

		SliceResponse<ChatRoomInfoDto> response = new SliceResponse<>(chatRooms);

		ResultDto<SliceResponse<ChatRoomInfoDto>> resultDto = ResultDto.res(
			CHATROOM_LIST_SUCCESS.getStatusCode(),
			CHATROOM_LIST_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(CHATROOM_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
