package org.omocha.domain.chat;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.chat.exception.ChatRoomAccessException;
import org.omocha.domain.chat.exception.ChatRoomAlreadyExistException;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final AuctionReader auctionReader;
	private final ChatStore chatStore;
	private final ChatReader chatReader;
	private final ChatMessageSender chatMessageSender;
	private final MemberReader memberReader;

	@Override
	@Transactional
	public void addChatRoom(ChatCommand.AddChatRoom addChatRoom) {
		log.info("ChatRoom add request: {}", addChatRoom);

		Auction auction = auctionReader.getAuction(addChatRoom.auctionId());

		if (chatReader.existsByAuctionId(auction.getAuctionId())) {
			throw new ChatRoomAlreadyExistException(auction.getAuctionId());
		}

		ChatRoom chatRoom = addChatRoom.toEntity(auction);
		chatStore.store(chatRoom);

		log.info("chatRoom added: {}", chatRoom.getRoomId());
	}

	@Override
	@Transactional(readOnly = true)
	public Slice<ChatInfo.RetrieveMyChatRoom> retrieveMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveCommand,
		Pageable pageable
	) {

		return chatReader.getMyChatRoomList(retrieveCommand, pageable);
	}

	@Override
	@Transactional
	public Chat saveChatMessage(ChatCommand.AddChatMessage message) {

		chatReader.getChatRoom(message.roomId());

		Chat chat = message.toEntity();
		Chat savedChat = chatStore.store(chat);

		log.info("Message [{}] sent by member: {} saved", message.message(), message.senderMemberId());
		return savedChat;
	}

	@Override
	@Transactional(readOnly = true)
	public void sendChatMessage(Chat savedChat) {

		Member sender = memberReader.getMember(savedChat.getSenderMemberId());

		ChatInfo.RetrieveChatRoomMessage chatMessage =
			ChatInfo.RetrieveChatRoomMessage.toInfo(sender, savedChat);

		chatMessageSender.sendMessage("/sub/channel/" + savedChat.getRoomId(), chatMessage);

		log.info("Message [{}] sent by member: {} to chat room: {}", chatMessage.message(),
			chatMessage.nickname(), savedChat.getRoomId());
	}

	@Override
	@Transactional(readOnly = true)
	public Slice<ChatInfo.RetrieveChatRoomMessage> retrieveChatRoomMessages(
		ChatCommand.RetrieveChatRoomMessage chatMessageCommand,
		Pageable pageable) {
		ChatRoom chatRoom = chatReader.getChatRoom(chatMessageCommand.roomId());

		if (!chatRoom.validateParticipant(chatMessageCommand.memberId())) {
			throw new ChatRoomAccessException(chatMessageCommand.memberId());
		}

		return chatReader.getChatRoomMessageList(chatMessageCommand, pageable);
	}
}
