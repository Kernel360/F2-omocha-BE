package org.omocha.infra.notification;

import java.util.UUID;

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
	public void emitterStore(Long memberId, UUID emitterId, SseEmitter emitter) {
		notificationRepository.storeSseEmitter(memberId, emitterId, emitter);
	}

	@Override
	public void emitterDelete(Long memberId, UUID emitterId) {
		notificationRepository.removeSseEmitter(memberId, emitterId);
	}

	@Override
	public Notification notificationStore(
		Long memberId,
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

		return notificationRepository.save(notification);
	}
}
