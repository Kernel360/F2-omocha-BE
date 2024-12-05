package org.omocha.infra.chat;

import org.omocha.domain.chat.ChatInfo;
import org.omocha.domain.chat.ChatMessageSender;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatMessageSenderImpl implements ChatMessageSender {

	private final SimpMessageSendingOperations messagingTemplate;

	@Override
	public void sendMessage(String path, ChatInfo.RetrieveChatRoomMessage message) {
		messagingTemplate.convertAndSend(path, message);
	}
}
