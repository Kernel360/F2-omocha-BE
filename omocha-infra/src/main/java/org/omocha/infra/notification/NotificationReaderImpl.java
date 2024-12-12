package org.omocha.infra.notification;

import java.util.List;

import org.omocha.domain.notification.Notification;
import org.omocha.domain.notification.NotificationReader;
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
			.orElseThrow(() -> new RuntimeException("test"));
	}
}
