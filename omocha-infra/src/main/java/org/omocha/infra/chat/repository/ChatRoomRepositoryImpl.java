package org.omocha.infra.chat.repository;

import static org.omocha.domain.auction.QAuction.*;
import static org.omocha.domain.chat.QChat.*;
import static org.omocha.domain.chat.QChatRoom.*;
import static org.omocha.domain.conclude.QConclude.*;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.chat.ChatCommand;
import org.omocha.domain.chat.ChatInfo;
import org.omocha.domain.chat.QChatInfo_RetrieveMyChatRoom;
import org.omocha.domain.member.QMember;
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

	public ChatRoomRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Slice<ChatInfo.RetrieveMyChatRoom> getMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveMyChatRoom,
		Pageable pageable
	) {
		QMember seller = new QMember("seller");
		QMember buyer = new QMember("buyer");

		Expression<LocalDateTime> lastMessageTime = JPAExpressions
			.select(chat.createdAt.max())
			.from(chat)
			.where(chat.roomId.eq(chatRoom.roomId));

		DateTimeExpression<LocalDateTime> lastMessageTimeOrder =
			Expressions.asDateTime(lastMessageTime);

		DateTimeExpression<LocalDateTime> orderTime =
			lastMessageTimeOrder.coalesce(chatRoom.createdAt);

		Expression<String> lastMessageContent = JPAExpressions
			.select(chat.message)
			.from(chat)
			.where(chat.roomId.eq(chatRoom.roomId)
				.and(chat.createdAt.eq(
					JPAExpressions.select(chat.createdAt.max())
						.from(chat)
						.where(chat.roomId.eq(chatRoom.roomId))
				))
			)
			.limit(1);

		List<ChatInfo.RetrieveMyChatRoom> chatRooms = queryFactory
			.select(new QChatInfo_RetrieveMyChatRoom(
				chatRoom.auctionId,
				chatRoom.roomId,
				chatRoom.roomName,
				seller.memberId,
				seller.nickname,
				seller.profileImageUrl,
				auction.thumbnailPath,
				conclude.concludePrice,
				buyer.memberId,
				buyer.nickname,
				buyer.profileImageUrl,
				chatRoom.createdAt,
				lastMessageTime,
				lastMessageContent
			))
			.from(chatRoom)
			.leftJoin(seller).on(seller.memberId.eq(chatRoom.sellerMemberId))
			.leftJoin(buyer).on(buyer.memberId.eq(chatRoom.buyerMemberId))
			.leftJoin(auction).on(auction.auctionId.eq(chatRoom.auctionId))
			.leftJoin(conclude).on(conclude.auction.eq(auction))
			.where(chatRoom.buyerMemberId.eq(retrieveMyChatRoom.memberId())
				.or(chatRoom.sellerMemberId.eq(retrieveMyChatRoom.memberId())))
			.orderBy(
				orderTime.desc()
			)
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = chatRooms.size() > pageable.getPageSize();
		if (hasNext) {
			chatRooms.remove(pageable.getPageSize());
		}

		return new SliceImpl<>(chatRooms, pageable, hasNext);
	}
}