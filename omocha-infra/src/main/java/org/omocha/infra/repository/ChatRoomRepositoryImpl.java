package org.omocha.infra.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.omocha.domain.auction.QAuction;
import org.omocha.domain.auction.chat.ChatCommand;
import org.omocha.domain.auction.chat.ChatInfo;
import org.omocha.domain.auction.chat.QChat;
import org.omocha.domain.auction.chat.QChatInfo_MyChatRoomInfo;
import org.omocha.domain.auction.chat.QChatRoom;
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
	public Slice<ChatInfo.MyChatRoomInfo> findMyChatRooms(
		ChatCommand.RetrieveMyChatRoom retrieveMyChatRoom,
		Pageable pageable
	) {

		QChatRoom chatRoom = QChatRoom.chatRoom;
		QChat chat = QChat.chat;
		QMember seller = new QMember("seller");
		QMember buyer = new QMember("buyer");
		QAuction auction = QAuction.auction;

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

		List<ChatInfo.MyChatRoomInfo> chatRooms = queryFactory
			.select(new QChatInfo_MyChatRoomInfo(
				chatRoom.auctionId,
				chatRoom.roomId,
				chatRoom.roomName,
				seller.memberId,
				seller.nickname,
				seller.profileImageUrl,
				auction.thumbnailPath,
				// TODO : concludePrice 추가해야함
				buyer.memberId,
				buyer.nickname,
				buyer.profileImageUrl,
				chatRoom.createdAt,
				lastMessageTime,
				lastMessageContent
			))
			.from(chatRoom)
			.leftJoin(seller).on(seller.memberId.eq(chatRoom.sellerId))
			.leftJoin(buyer).on(buyer.memberId.eq(chatRoom.buyerId))
			.leftJoin(auction).on(auction.auctionId.eq(chatRoom.auctionId))
			.where(chatRoom.buyerId.eq(retrieveMyChatRoom.memberId())
				.or(chatRoom.sellerId.eq(retrieveMyChatRoom.memberId())))
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