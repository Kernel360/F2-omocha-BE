package org.omocha.domain.chat;

public interface ChatMessageSender {
	void sendMessage(String path, ChatInfo.RetrieveChatRoomMessage message);
}
