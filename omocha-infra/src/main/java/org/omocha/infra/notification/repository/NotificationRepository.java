package org.omocha.infra.notification.repository;

import java.util.List;

import org.omocha.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, SseEmitterCache {

	List<Notification> findAllByMemberMemberIdAndReadOrderByCreatedAt(Long memberId, boolean read);
}
