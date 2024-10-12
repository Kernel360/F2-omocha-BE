package org.auction.client.chat.interfaces;

import static org.auction.client.common.code.ChatCode.*;

import org.auction.client.chat.application.ChatRoomService;
import org.auction.client.chat.interfaces.response.ChatRoomListResponse;
import org.auction.client.chat.interfaces.response.CreateChatRoomResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatroom")
public class ChatRoomController implements ChatRoomApi {

	private final ChatRoomService chatRoomService;

	// EXPLAIN : 채팅방 생성
	// TODO : 낙찰이 되면 바로 채팅방 생성 로직으로 변경
	@PostMapping("/{auctionId}")
	public ResponseEntity<ResultDto<CreateChatRoomResponse>> chatRoomSave(
		@PathVariable Long auctionId,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {

		// TODO : UserPrincipal 이 유효한 값인지 확인하는 로직 추가 필요

		// 채팅방 생성
		CreateChatRoomResponse response = chatRoomService.addChatRoom(userPrincipal.getMemberEntity(), auctionId);

		// 응답 생성
		ResultDto<CreateChatRoomResponse> resultDto = ResultDto.res(
			CHATROOM_CREATE_SUCCESS.getStatusCode(),
			CHATROOM_CREATE_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(CHATROOM_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	// TODO : pagination 으로 변경하기
	@GetMapping
	public ResponseEntity<ResultDto<ChatRoomListResponse>> findChatRooms(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		log.info("Fetching all chat rooms for user ID: {}", userPrincipal.getMemberEntity().getMemberId());

		// 채팅방 목록 조회
		ChatRoomListResponse response = chatRoomService.findMyChatRooms(userPrincipal.getMemberEntity());

		// 응답 생성
		ResultDto<ChatRoomListResponse> resultDto = ResultDto.res(
			CHATROOM_LIST_SUCCESS.getStatusCode(),
			CHATROOM_LIST_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(CHATROOM_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

}
