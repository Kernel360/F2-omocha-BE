package org.omocha.infra.repository;

import java.util.List;

import org.omocha.domain.auction.chat.ChatCommand;
import org.omocha.domain.auction.chat.ChatInfo;
import org.omocha.domain.auction.chat.QChat;
import org.omocha.domain.auction.chat.QChatInfo_ChatMessage;
import org.omocha.domain.member.QMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ChatRepositoryImpl implements ChatRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public ChatRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<ChatInfo.ChatMessage> findChatMessagesByRoomId(
		ChatCommand.RetrieveChatRoomMessage retrieveMessage,
		Pageable pageable
	) {
		QChat chat = QChat.chat;
		QMember member = QMember.member;

		List<ChatInfo.ChatMessage> messages = queryFactory
			.select(new QChatInfo_ChatMessage(
				chat.messageType,
				chat.senderId,
				chat.roomId,
				member.nickname,
				member.profileImageUrl,
				chat.message,
				chat.createdAt
			))
			.from(chat)
			.leftJoin(member).on(member.memberId.eq(chat.senderId))
			.where(chat.roomId.eq(retrieveMessage.roomId())
				.and(retrieveMessage.cursor() != null ? chat.createdAt.lt(retrieveMessage.cursor()) : null))
			.orderBy(chat.createdAt.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = messages.size() > pageable.getPageSize();

		if (hasNext) {
			messages.remove(pageable.getPageSize());
		}

		return new SliceImpl<>(messages, pageable, hasNext);
	}
}
