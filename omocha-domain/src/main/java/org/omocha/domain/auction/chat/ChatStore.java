package org.omocha.domain.auction.chat;

public interface ChatStore {

	ChatRoom store(ChatRoom chatRoom);

	Chat store(Chat chat);
}
