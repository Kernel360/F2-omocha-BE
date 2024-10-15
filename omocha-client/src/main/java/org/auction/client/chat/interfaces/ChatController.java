package org.auction.client.chat.interfaces;

import org.auction.client.chat.application.ChatService;
import org.auction.client.chat.interfaces.request.ChatMessageRequest;
import org.auction.client.chat.interfaces.response.ChatMessageResponse;
import org.auction.client.member.application.MemberService;
import org.auction.domain.chat.domain.entity.ChatEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

	private final SimpMessageSendingOperations messagingTemplate;
	private final ChatService chatService;
	private final MemberService memberService;

	@MessageMapping("/{roomId}/messages")
	public void chat(
		@DestinationVariable("roomId") Long roomId,
		ChatMessageRequest chatRequest
	) {

		log.info("Received message: {}", chatRequest);

		MemberEntity member =
			memberService.findMemberByMemberId(chatRequest.senderId());

		// 채팅 메시지 저장
		ChatEntity chat = chatService.createChat(
			roomId, member, chatRequest.message(), chatRequest.messageType()
		);

		// 클라이언트에게 전달할 메시지 생성
		ChatMessageResponse chatMessage = ChatMessageResponse.toDto(chat);

		// 클라이언트에게 메시지 전달
		messagingTemplate.convertAndSend("/sub/channel/" + roomId, chatMessage);

		log.info("Message [{}] sent by member: {} to chat room: {}", chatMessage.message(),
			chatMessage.senderNickName(), roomId);
	}

}
