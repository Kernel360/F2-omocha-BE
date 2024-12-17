package org.omocha.infra.notification.repository;

import java.util.List;

import org.omocha.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long>, SseEmitterCache {

	List<Notification> findAllByMemberMemberIdAndReadOrderByCreatedAt(Long memberId, boolean read);

	@Modifying
	@Query("UPDATE Notification n SET n.read = true WHERE n.notificationId IN :notificationIds AND n.member.memberId = :memberId")
	void modifyAllAsRead(@Param("memberId") Long memberId, @Param("notificationIds") List<Long> notificationIds);
}
