package org.auction.domain.chat.infrastructure.custom;

import static org.auction.domain.chat.domain.entity.QChatEntity.*;

import java.util.List;

import org.auction.domain.chat.domain.entity.ChatEntity;
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
	public Slice<ChatEntity> findChatMessagesByRoomId(Long roomId, Pageable pageable) {
		List<ChatEntity> messages = queryFactory
			.selectFrom(chatEntity)
			.where(chatEntity.chatRoom.chatRoomId.eq(roomId))
			.orderBy(chatEntity.createdAt.desc()) // 최신 메시지 순으로 정렬!
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return new SliceImpl<>(messages, pageable, hasNextPage(messages, pageable.getPageSize()));
	}

	private boolean hasNextPage(List<ChatEntity> messages, int pageSize) {
		if (messages.size() > pageSize) {
			messages.remove(pageSize);
			return true;
		}
		return false;
	}
}
