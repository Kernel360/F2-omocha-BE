package org.omocha.api.chat;

import static org.omocha.domain.common.code.SuccessCode.*;

import java.time.LocalDateTime;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.chat.dto.ChatDtoMapper;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.response.SliceResponseDto.SliceResponse;
import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatInfo;
import org.omocha.domain.common.util.PageSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
@RequestMapping("/api/v2/chatroom")
public class ChatRoomController implements ChatRoomApi {

	private final ChatDtoMapper chatDtoMapper;
	private final ChatFacade chatFacade;
	private final PageSort pageSort;

	// TODO : Front 테스트 용입니다
	@PostMapping("/{auctionId}")
	public ResponseEntity<ResultDto> chatRoomAdd(
		@PathVariable Long auctionId,
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		Long buyerMemberId = userPrincipal.getId();

		ChatCommand.AddChatRoom chatRoomCommand = chatDtoMapper.toCommand(
			auctionId, buyerMemberId);
		chatFacade.addChatRoom(chatRoomCommand);

		ResultDto<Void> result = ResultDto.res(
			CHATROOM_CREATE_SUCCESS.getStatusCode(),
			CHATROOM_CREATE_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(CHATROOM_CREATE_SUCCESS.getHttpStatus())
			.body(result);
	}

	// EXPLAIN : 현재 참여중인 채팅방 List
	@GetMapping("")
	public ResponseEntity<ResultDto<SliceResponse<ChatInfo.RetrieveMyChatRoom>>> myChatRoomList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(required = false) String sort
	) {
		log.info("Fetching chat rooms for memberId: {}", userPrincipal.getId());

		Pageable pageable = PageRequest.of(page, size);

		Long memberId = userPrincipal.getId();
		ChatCommand.RetrieveMyChatRoom chatRoomCommand = chatDtoMapper.toCommand(memberId);
		Slice<ChatInfo.RetrieveMyChatRoom> myChatRoomInfo = chatFacade.retrieveMyChatRooms(chatRoomCommand,
			pageable);
		SliceResponse<ChatInfo.RetrieveMyChatRoom> response =
			new SliceResponse<>(myChatRoomInfo);

		ResultDto<SliceResponse<ChatInfo.RetrieveMyChatRoom>> result = ResultDto.res(
			CHATROOM_LIST_SUCCESS.getStatusCode(),
			CHATROOM_LIST_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(CHATROOM_LIST_SUCCESS.getStatusCode())
			.body(result);

	}

	// EXPLAIN : 채팅 대화 List
	// TODO : chatting message sorting 기준 추가해야함, chatting방 찾을 수 없는 예오
	@GetMapping("/{roomId}")
	public ResponseEntity<ResultDto<SliceResponse<ChatInfo.RetrieveChatRoomMessage>>> chatMessageList(
		@PathVariable Long roomId,
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(required = false) LocalDateTime cursor,
		@RequestParam(defaultValue = "createdAt", required = false) String sort,
		@RequestParam(defaultValue = "DESC", required = false) String direction,
		@RequestParam(defaultValue = "10", required = false) int size
	) {
		Pageable pageable = PageRequest.of(0, size);

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		Long memberId = userPrincipal.getId();

		ChatCommand.RetrieveChatRoomMessage messageCommand =
			chatDtoMapper.toCommand(roomId, memberId, cursor);

		Slice<ChatInfo.RetrieveChatRoomMessage> messageInfo =
			chatFacade.retrieveChatRoomMessages(messageCommand, sortPage);

		SliceResponse<ChatInfo.RetrieveChatRoomMessage> response = new SliceResponse<>(
			messageInfo);

		ResultDto<SliceResponse<ChatInfo.RetrieveChatRoomMessage>> result = ResultDto.res(
			CHATROOM_MESSAGES_SUCCESS.getStatusCode(),
			CHATROOM_MESSAGES_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(CHATROOM_MESSAGES_SUCCESS.getHttpStatus())
			.body(result);
	}
}
