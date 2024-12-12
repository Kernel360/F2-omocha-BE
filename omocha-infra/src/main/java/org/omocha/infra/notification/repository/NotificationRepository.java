package org.omocha.infra.notification.repository;

import org.omocha.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, SseEmitterCache {
}
