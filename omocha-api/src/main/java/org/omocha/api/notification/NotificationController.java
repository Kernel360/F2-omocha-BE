package org.omocha.api.notification;

import static org.omocha.domain.common.code.SuccessCode.*;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.notification.dto.NotificationDto;
import org.omocha.api.notification.dto.NotificationDtoMapper;
import org.omocha.domain.notification.NotificationCommand;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/notifications")
public class NotificationController implements NotificationApi {

	private final NotificationDtoMapper notificationDtoMapper;
	private final NotificationFacade notificationFacade;

	@Override
	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "lastEventId", required = false, defaultValue = "") String lastEventId
	) {
		NotificationCommand.Connect connectCommand = notificationDtoMapper.toCommand(
			userPrincipal.getId(), lastEventId);

		SseEmitter emitter = notificationFacade.connect(connectCommand);

		return ResponseEntity.ok(emitter);
	}

	@Override
	@PostMapping(value = "/read/{notification_id}")
	public ResponseEntity<ResultDto<Void>> read(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("notification_id") Long notificationId
	) {
		NotificationCommand.Read readCommand = notificationDtoMapper.toCommand(
			userPrincipal.getId(), notificationId);

		notificationFacade.read(readCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			NOTIFICATION_READ_SUCCESS.getStatusCode(),
			NOTIFICATION_READ_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(NOTIFICATION_READ_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping(value = "/readAll")
	public ResponseEntity<ResultDto<Void>> readAll(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody NotificationDto.ReadAll readAllDto
	) {
		NotificationCommand.ReadAll readCommand = notificationDtoMapper.toCommand(userPrincipal.getId(), readAllDto);

		notificationFacade.readAll(readCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			NOTIFICATION_READ_SUCCESS.getStatusCode(),
			NOTIFICATION_READ_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(NOTIFICATION_READ_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
