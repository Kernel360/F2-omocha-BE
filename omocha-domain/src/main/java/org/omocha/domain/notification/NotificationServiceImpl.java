package org.omocha.domain.notification;

import static org.omocha.domain.notification.enums.EventName.*;
import static org.omocha.domain.notification.enums.NotificationCode.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.bid.BidReader;
import org.omocha.domain.common.util.JsonUtils;
import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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
	public SseEmitter connect(NotificationCommand.Connect connectCommand) {
		Long memberId = connectCommand.memberId();
		UUID emitterId = UUID.randomUUID();

		SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
		notificationStore.emitterStore(memberId, emitterId, emitter);

		configureEmitterEvents(memberId, emitter, emitterId);
		sendSseEvent(emitter, CONNECT, memberId, "Connect Success");

		return emitter;
	}

	@Override
	public void sendBidEvent(
		Long auctionId,
		Long sellerMemberId,
		Long newBuyerMemberId
	) {
		String auctionData = convertAuctionToJson(auctionId);

		sendNotification(BID, sellerMemberId, BID_SELLER, auctionData, NotificationInfo.AuctionResponse.class);

		List<Long> buyerMemberIdList = bidReader.getBuyerList(auctionId);
		buyerMemberIdList.forEach(existingBidderMemberId -> {
			if (!newBuyerMemberId.equals(existingBidderMemberId)) {
				sendNotification(
					BID,
					existingBidderMemberId,
					BID_BUYER,
					auctionData,
					NotificationInfo.AuctionResponse.class
				);
			}
		});
	}

	@Override
	public void sendConcludeEvent(
		Long auctionId,
		Long sellerMemberId,
		Long buyerMemberId
	) {
		String auctionData = convertAuctionToJson(auctionId);

		if (buyerMemberId == null) {
			sendNotification(
				CONCLUDE,
				sellerMemberId,
				CONCLUDE_NO_BIDS,
				auctionData,
				NotificationInfo.AuctionResponse.class
			);
			return;
		}

		sendNotification(
			CONCLUDE,
			sellerMemberId,
			CONCLUDE_SELLER,
			auctionData,
			NotificationInfo.AuctionResponse.class
		);

		List<Long> buyerMemberIdList = bidReader.getBuyerList(auctionId);
		buyerMemberIdList.forEach(existingBidderMemberId -> {
			if (buyerMemberId.equals(existingBidderMemberId)) {
				sendNotification(
					CONCLUDE,
					existingBidderMemberId,
					CONCLUDE_BUYER,
					auctionData,
					NotificationInfo.AuctionResponse.class
				);
				return;
			}

			sendNotification(
				CONCLUDE,
				existingBidderMemberId,
				CONCLUDE_OTHER_BUYER,
				auctionData,
				NotificationInfo.AuctionResponse.class
			);
		});
	}

	@Override
	public void read(NotificationCommand.Read readCommand) {
		Notification notification = notificationReader.getNotification(readCommand.notificationId());
		notification.modifyAsRead();
	}

	private <T> void sendNotification(
		EventName eventName,
		Long memberId,
		NotificationCode notificationCode,
		String data,
		Class<T> responseType
	) {
		Notification notification = notificationStore.notificationStore(memberId, eventName, notificationCode, data);

		NotificationInfo.RootResponse<T> rootResponse = NotificationInfo.RootResponse
			.toInfo(notification, responseType);

		String notificationMessage = JsonUtils.toJson(rootResponse);

		notificationReader.getEmitterList(memberId)
			.forEach(emitter -> sendSseEvent(emitter, eventName, memberId, notificationMessage));
	}

	private void configureEmitterEvents(Long memberId, SseEmitter emitter, UUID emitterId) {
		emitter.onCompletion(() -> {
			notificationStore.emitterDelete(memberId, emitterId);
			log.info("onCompletion");
		});

		emitter.onTimeout(() -> {
			notificationStore.emitterDelete(memberId, emitterId);
			log.info("onTimeout");
		});

		emitter.onError((e) -> {
			notificationStore.emitterDelete(memberId, emitterId);
			log.info("onError");
		});
	}

	private String convertAuctionToJson(Long auctionId) {
		Auction auction = auctionReader.getAuction(auctionId);
		NotificationInfo.AuctionResponse auctionResponse = NotificationInfo.AuctionResponse.toInfo(auction);

		return JsonUtils.toJson(auctionResponse);
	}

	private void sendSseEvent(SseEmitter emitter, EventName eventName, Long memberId, String data) {
		try {
			emitter.send(
				SseEmitter.event()
					.name(eventName.toString())
					.id(String.valueOf(memberId))
					.data(data, MediaType.APPLICATION_JSON)
					.reconnectTime(RECONNECTION_TIMEOUT)
			);
		} catch (IOException e) {
			throw new RuntimeException("Failed to send event to emitter", e);
		}
	}
}
