package org.auction.client.exception.chat;

import org.auction.client.common.code.ChatCode;

import lombok.Getter;

@Getter
public class ChatException extends RuntimeException {

	private final ChatCode chatCode;
	private final String detailMessage;

	public ChatException(
		ChatCode chatCode
	) {
		super(chatCode.getResultMsg());
		this.chatCode = chatCode;
		this.detailMessage = chatCode.getResultMsg();
	}

	public ChatException(
		ChatCode chatCode,
		String detailMessage
	) {
		super(chatCode.getResultMsg());
		this.chatCode = chatCode;
		this.detailMessage = detailMessage;
	}

}
