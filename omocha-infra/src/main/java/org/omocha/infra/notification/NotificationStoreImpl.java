package org.omocha.infra.notification;

import java.util.List;

import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.omocha.domain.notification.Notification;
import org.omocha.domain.notification.NotificationStore;
import org.omocha.domain.notification.enums.EventName;
import org.omocha.domain.notification.enums.NotificationCode;
import org.omocha.infra.notification.repository.NotificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationStoreImpl implements NotificationStore {

	private final NotificationRepository notificationRepository;
	private final MemberReader memberReader;

	@Override
	public void emitterStore(
		Long memberId,
		String emitterId,
		SseEmitter emitter,
		Long EXPIRATION
	) {
		notificationRepository.storeSseEmitter(memberId, emitterId, emitter, EXPIRATION);
	}

	@Override
	public void emitterDelete(Long memberId, String emitterId) {
		notificationRepository.removeSseEmitter(memberId, emitterId);
	}

	@Override
	public Notification store(
		Long memberId,
		String eventId,
		EventName eventName,
		NotificationCode notificationCode,
		String data
	) {
		Member member = memberReader.getMember(memberId);

		Notification notification = Notification.builder()
			.member(member)
			.eventName(eventName)
			.notificationCode(notificationCode)
			.data(data)
			.build();

		notificationRepository.storeNotificationCache(eventId, notification);

		return notificationRepository.save(notification);
	}

	@Override
	public void bulkRead(Long memberId, List<Long> notificationIdList) {
		notificationRepository.modifyAllAsRead(memberId, notificationIdList);
	}
}
