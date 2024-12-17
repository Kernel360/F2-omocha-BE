package org.omocha.api.notification;

import org.omocha.domain.notification.NotificationCommand;
import org.omocha.domain.notification.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationFacade {

	private final NotificationService notificationService;

	public SseEmitter connect(NotificationCommand.Connect connectCommand) {
		return notificationService.connect(connectCommand);
	}

	public void read(NotificationCommand.Read readCommand) {
		notificationService.read(readCommand);
	}

	public void readAll(NotificationCommand.ReadAll readAllCommand) {
		notificationService.readAll(readAllCommand);
	}
}