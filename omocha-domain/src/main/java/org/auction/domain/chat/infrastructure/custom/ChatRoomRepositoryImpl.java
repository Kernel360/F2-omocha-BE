package org.auction.domain.chat.infrastructure.custom;

import java.time.LocalDateTime;
import java.util.List;

import org.auction.domain.chat.domain.dto.ChatRoomInfoDto;
import org.auction.domain.chat.domain.dto.QChatRoomInfoDto;
import org.auction.domain.chat.domain.entity.QChatEntity;
import org.auction.domain.chat.domain.entity.QChatRoomEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
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
	public Slice<ChatRoomInfoDto> findChatRoomsByUser(
		MemberEntity memberEntity,
		Pageable pageable
	) {
		QChatRoomEntity chatRoom = QChatRoomEntity.chatRoomEntity;
		QChatEntity chatSub = QChatEntity.chatEntity;

		Expression<LocalDateTime> lastMessagetime = JPAExpressions
			.select(chatSub.createdAt.max())
			.from(chatSub)
			.where(chatSub.chatRoom.eq(chatRoom));

		DateTimeExpression<LocalDateTime> lastMessageTimeOrder =
			Expressions.asDateTime(lastMessagetime);

		// 최신 메시지 또는 생성 시간이 최근이 채팅방이 상단에 표시
		DateTimeExpression<LocalDateTime> orderTime =
			lastMessageTimeOrder.coalesce(chatRoom.createdAt);

		List<ChatRoomInfoDto> chatRooms = queryFactory
			.select(new QChatRoomInfoDto(
				chatRoom.auctionId,
				chatRoom.chatRoomId,
				chatRoom.roomName,
				chatRoom.seller.memberId,
				chatRoom.seller.nickname,
				chatRoom.concludePrice,
				chatRoom.buyer.memberId,
				chatRoom.buyer.nickname,
				chatRoom.createdAt,
				lastMessagetime
			))
			.from(chatRoom)
			.where(chatRoom.buyer.eq(memberEntity)
				.or(chatRoom.seller.eq(memberEntity)))
			.orderBy(orderTime.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = chatRooms.size() > pageable.getPageSize();
		if (hasNext) {
			chatRooms.remove(pageable.getPageSize());
		}

		return new SliceImpl<>(chatRooms, pageable, hasNext);
	}
}
