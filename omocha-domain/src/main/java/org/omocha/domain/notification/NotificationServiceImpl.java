package org.omocha.domain.notification;

import static org.omocha.domain.notification.enums.EventName.*;
import static org.omocha.domain.notification.enums.NotificationCode.*;

import java.io.IOException;
import java.util.List;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.bid.BidReader;
import org.omocha.domain.common.util.JsonUtils;
import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;
import org.omocha.domain.notification.exception.NotificationAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationStore notificationStore;
	private final NotificationReader notificationReader;
	private final AuctionReader auctionReader;
	private final BidReader bidReader;

	private static final long SSE_TIMEOUT = 1000L * 60 * 5;
	private static final long RECONNECTION_TIMEOUT = 1000L;

	@Override
	@Transactional
	public SseEmitter connect(NotificationCommand.Connect connectCommand) {
		Long memberId = connectCommand.memberId();
		String eventId = createEmitterId(memberId);

		SseEmitter emitter = createEmitter(memberId, eventId);
		sendSseEvent(emitter, eventId, CONNECT, memberId, "Connect Success");

		String lastEventId = connectCommand.lastEventId();
		if (!lastEventId.isEmpty()) {
			sendLostData(emitter, memberId, lastEventId);
			return emitter;
		}

		sendNotReadData(emitter, memberId);
		return emitter;
	}

	private SseEmitter createEmitter(Long memberId, String emitterId) {
		SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
		emitter.onCompletion(() -> notificationStore.emitterDelete(memberId, emitterId));
		emitter.onTimeout(emitter::complete);
		emitter.onError((e) -> notificationStore.emitterDelete(memberId, emitterId));

		notificationStore.emitterStore(memberId, emitterId, emitter, SSE_TIMEOUT);
		return emitter;
	}

	private void sendLostData(
		SseEmitter emitter,
		Long memberId,
		String lastEventId
	) {
		notificationReader.getLostNotificationList(memberId).entrySet().stream()
			.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
			.forEach(entry -> sendNotificationData(emitter, entry.getKey(), memberId, entry.getValue()));
	}

	private void sendNotReadData(SseEmitter emitter, Long memberId) {
		notificationReader.getNotReadNotificationList(memberId)
			.forEach(notification -> sendNotificationData(emitter, createEmitterId(memberId), memberId, notification));
	}

	private void sendNotificationData(
		SseEmitter emitter,
		String eventId,
		Long memberId,
		Notification notification
	) {
		String notificationData = JsonUtils.toJson(
			NotificationInfo.RootResponse.toInfo(notification, NotificationInfo.AuctionResponse.class)
		);

		sendSseEvent(emitter, eventId, notification.getEventName(), memberId, notificationData);
	}

	@Override
	@Transactional
	public void sendBidEvent(Long auctionId, Long newBuyerMemberId) {
		Auction auction = auctionReader.getAuction(auctionId);
		String auctionData = convertAuctionToJson(auction);

		notifyMember(BID, auction.getMemberId(), BID_SELLER, auctionData);
		notifyBidBuyers(auctionId, newBuyerMemberId, auctionData);
	}

	private void notifyBidBuyers(
		Long auctionId,
		Long newBuyerMemberId,
		String auctionData
	) {
		bidReader.getBuyerList(auctionId).stream()
			.filter(existingBidderId -> !existingBidderId.equals(newBuyerMemberId))
			.forEach(bidderId -> notifyMember(BID, bidderId, BID_BUYER, auctionData));
	}

	@Override
	@Transactional
	public void sendInstantBuyEvent(Long auctionId, Long buyerMemberId) {
		Auction auction = auctionReader.getAuction(auctionId);
		String auctionData = convertAuctionToJson(auction);

		notifyMember(CONCLUDE, auction.getMemberId(), CONCLUDE_SELLER, auctionData);
		notifyConcludeBuyers(auctionId, auction.getConclude().getBuyer().getMemberId(), auctionData);
	}

	@Override
	@Transactional
	public void sendConcludeEvent(List<Long> concludedAuctionIdList) {
		concludedAuctionIdList.forEach(auctionId -> {
			Auction auction = auctionReader.getAuction(auctionId);
			String auctionData = convertAuctionToJson(auction);

			if (auction.getConclude() == null) {
				notifyMember(CONCLUDE, auction.getMemberId(), CONCLUDE_NO_BIDS, auctionData);
				return;
			}

			notifyMember(CONCLUDE, auction.getMemberId(), CONCLUDE_SELLER, auctionData);
			notifyConcludeBuyers(auctionId, auction.getConclude().getBuyer().getMemberId(), auctionData);
		});
	}

	private void notifyConcludeBuyers(
		Long auctionId,
		Long buyerMemberId,
		String auctionData
	) {
		bidReader.getBuyerList(auctionId).forEach(bidderId -> {
			NotificationCode code = buyerMemberId.equals(bidderId) ? CONCLUDE_BUYER : CONCLUDE_OTHER_BUYER;
			notifyMember(CONCLUDE, bidderId, code, auctionData);
		});
	}

	private void notifyMember(
		EventName eventName,
		Long memberId,
		NotificationCode code,
		String data
	) {
		String eventId = createEmitterId(memberId);
		Notification notification = notificationStore.store(memberId, eventId, eventName, code, data);

		String notificationData = JsonUtils.toJson(
			NotificationInfo.RootResponse.toInfo(notification, NotificationInfo.AuctionResponse.class)
		);

		notificationReader.getEmitterList(memberId).forEach(emitter ->
			sendSseEvent(emitter, eventId, eventName, memberId, notificationData));
	}

	private void sendSseEvent(
		SseEmitter emitter,
		String eventId,
		EventName eventName,
		Long memberId,
		String data
	) {
		try {
			if (emitter != null) {
				emitter.send(SseEmitter.event()
					.name(eventName.toString())
					.id(eventId)
					.data(data, MediaType.APPLICATION_JSON)
					.reconnectTime(RECONNECTION_TIMEOUT));
			}
		} catch (IOException e) {
			notificationStore.emitterDelete(memberId, eventId);
		}
	}

	private String createEmitterId(Long memberId) {
		return memberId + "_" + System.currentTimeMillis();
	}

	private String convertAuctionToJson(Auction auction) {
		return JsonUtils.toJson(NotificationInfo.AuctionResponse.toInfo(auction));
	}

	@Override
	@Transactional
	public void read(NotificationCommand.Read readCommand) {
		Notification notification = notificationReader.getNotification(readCommand.notificationId());

		Long readMemberId = readCommand.memberId();
		Long notifyMemberId = notification.getMember().getMemberId();
		if (!readMemberId.equals(notifyMemberId)) {
			throw new NotificationAccessException(readMemberId, notifyMemberId);
		}

		notification.modifyAsRead();
	}

	@Override
	@Transactional
	public void readAll(NotificationCommand.ReadAll readAllCommand) {
		notificationStore.bulkRead(readAllCommand.memberId(), readAllCommand.notificationIdList());
	}
}