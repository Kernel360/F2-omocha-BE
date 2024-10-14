package org.auction.domain.chat.infrastructure.custom;

import static org.auction.domain.chat.domain.entity.QChatEntity.*;
import static org.auction.domain.chat.domain.entity.QChatRoomEntity.*;

import java.util.List;

import org.auction.domain.chat.domain.entity.ChatRoomEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public ChatRoomRepositoryImpl(
		EntityManager em
	) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<ChatRoomEntity> findChatRoomsByUser(
		MemberEntity memberEntity,
		Pageable pageable
	) {
		List<ChatRoomEntity> chatRooms = queryFactory
			.select(chatRoomEntity)
			.from(chatRoomEntity)
			.leftJoin(chatEntity)
			.on(chatEntity.chatRoom.eq(chatRoomEntity))
			.where(chatRoomEntity.buyer.eq(memberEntity)
				.or(chatRoomEntity.seller.eq(memberEntity)))
			.orderBy(chatEntity.createdAt.max().desc())
			.groupBy(chatRoomEntity.chatRoomId)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return new SliceImpl<>(chatRooms, pageable, hasNextPage(chatRooms, pageable.getPageSize()));

	}

	private boolean hasNextPage(List<ChatRoomEntity> chatRooms, int pageSize) {
		if (chatRooms.size() > pageSize) {
			chatRooms.remove(pageSize);
			return true;
		}
		return false;
	}
}
