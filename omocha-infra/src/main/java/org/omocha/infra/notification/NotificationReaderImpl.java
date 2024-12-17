package org.omocha.infra.notification;

import java.util.List;
import java.util.Map;

import org.omocha.domain.notification.Notification;
import org.omocha.domain.notification.NotificationReader;
import org.omocha.domain.notification.exception.NotificationNotFoundException;
import org.omocha.infra.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationReaderImpl implements NotificationReader {

	private final NotificationRepository notificationRepository;

	@Override
	public List<SseEmitter> getEmitterList(Long memberId) {
		return notificationRepository.getSseEmitterList(memberId);
	}

	@Override
	public Notification getNotification(Long notificationId) {
		return notificationRepository.findById(notificationId)
			.orElseThrow(() -> new NotificationNotFoundException(notificationId));
	}

	@Override
	public Map<String, Notification> getLostNotificationList(Long memberId) {
		return notificationRepository.getEventCacheList(memberId);
	}

	@Override
	public List<Notification> getNotReadNotificationList(Long memberId) {
		return notificationRepository.findAllByMemberMemberIdAndReadOrderByCreatedAt(memberId, false);
	}
}
