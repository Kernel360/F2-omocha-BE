package org.auction.domain.chat.infrastructure.custom;

import static org.auction.domain.chat.domain.entity.QChatEntity.*;

import java.time.LocalDateTime;
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
	public Slice<ChatEntity> findChatMessagesByRoomId(
		Long roomId,
		LocalDateTime cursor,
		Pageable pageable
	) {
		List<ChatEntity> messages = queryFactory
			.selectFrom(chatEntity)
			.where(chatEntity.chatRoom.chatRoomId.eq(roomId)
				.and(cursor != null ? chatEntity.createdAt.lt(cursor) : null))
			.orderBy(chatEntity.createdAt.desc()) // 최신 메시지 순으로 정렬!
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = messages.size() > pageable.getPageSize();
		
		if (hasNext) {
			messages.remove(pageable.getPageSize());
		}

		return new SliceImpl<>(messages, pageable, hasNext);
	}

}
