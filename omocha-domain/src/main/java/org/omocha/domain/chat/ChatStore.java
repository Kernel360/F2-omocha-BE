package org.omocha.domain.chat;

public interface ChatStore {

	ChatRoom store(ChatRoom chatRoom);

	Chat store(Chat chat);
}
