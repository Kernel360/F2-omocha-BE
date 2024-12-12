package org.omocha.domain.notification;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
	SseEmitter connect(NotificationCommand.Connect connectCommand);

	void sendBidEvent(Long auctionId, Long sellerMemberId, Long buyerMemberId);

	void sendConcludeEvent(Long auctionId, Long sellerMemberId, Long buyerMemberId);

	void read(NotificationCommand.Read readCommand);
}
