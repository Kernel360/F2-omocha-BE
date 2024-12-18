package org.omocha.domain.notification;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
	SseEmitter connect(NotificationCommand.Connect connectCommand);

	void sendBidEvent(Long auctionId, Long buyerMemberId);

	void sendInstantBuyEvent(Long auctionId, Long buyerMemberId);

	void sendConcludeEvent(List<Long> concludedAuctionIdList);

	void read(NotificationCommand.Read readCommand);

	void readAll(NotificationCommand.ReadAll readAllCommand);
}
